// API Configuration for College ERP System
// This file dynamically handles API calls for both local and production environments

class ERPApiClient {
    constructor() {
        // Dynamically determine base URL
        this.baseURL = `${window.location.protocol}//${window.location.host}`;
        this.apiURL = `${this.baseURL}/api/auth`;
        this.healthURL = `${this.baseURL}/actuator/health`;
        
        console.log('ERP API Client initialized with base URL:', this.baseURL);
    }

    // Check server health
    async checkHealth() {
        try {
            const response = await fetch(this.healthURL);
            if (response.ok) {
                const data = await response.json();
                return data.status === 'UP';
            }
            return false;
        } catch (error) {
            console.error('Health check failed:', error);
            return false;
        }
    }

    // User login
    async login(credentials) {
        try {
            const response = await fetch(`${this.apiURL}/signin`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(credentials)
            });

            const data = await response.json();
            
            if (response.ok) {
                // Store JWT token
                localStorage.setItem('jwtToken', data.token);
                localStorage.setItem('userRole', data.role);
                localStorage.setItem('userName', data.username);
                return { success: true, data };
            } else {
                return { success: false, message: data.message || 'Login failed' };
            }
        } catch (error) {
            console.error('Login error:', error);
            return { success: false, message: 'Server connection failed' };
        }
    }

    // User registration
    async register(userData) {
        try {
            const response = await fetch(`${this.apiURL}/signup`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userData)
            });

            const data = await response.json();
            
            if (response.ok) {
                return { success: true, data };
            } else {
                return { success: false, message: data.message || 'Registration failed' };
            }
        } catch (error) {
            console.error('Registration error:', error);
            return { success: false, message: 'Server connection failed' };
        }
    }

    // Get user profile (with JWT token)
    async getProfile() {
        const token = localStorage.getItem('jwtToken');
        if (!token) {
            return { success: false, message: 'No token found' };
        }

        try {
            const response = await fetch(`${this.apiURL}/profile`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                }
            });

            const data = await response.json();
            
            if (response.ok) {
                return { success: true, data };
            } else {
                return { success: false, message: data.message || 'Failed to get profile' };
            }
        } catch (error) {
            console.error('Profile fetch error:', error);
            return { success: false, message: 'Server connection failed' };
        }
    }

    // Logout
    logout() {
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('userRole');
        localStorage.removeItem('userName');
        window.location.href = '/';
    }

    // Check if user is logged in
    isLoggedIn() {
        return !!localStorage.getItem('jwtToken');
    }

    // Get stored user role
    getUserRole() {
        return localStorage.getItem('userRole');
    }

    // Get stored username
    getUserName() {
        return localStorage.getItem('userName');
    }
}

// Create global API client instance
const apiClient = new ERPApiClient();

// Enhanced form handlers for login and registration
document.addEventListener('DOMContentLoaded', function() {
    // Status indicator
    const statusIndicator = document.getElementById('statusIndicator');
    const statusText = statusIndicator?.querySelector('#statusText');

    // Check server status on page load
    checkServerConnection();

    // Set up login form handler
    const loginForm = document.querySelector('#login-form, form[action*="login"]');
    if (loginForm) {
        setupLoginForm(loginForm);
    }

    // Set up registration form handler
    const registerForm = document.querySelector('#registration-form, form[action*="register"]');
    if (registerForm) {
        setupRegistrationForm(registerForm);
    }

    // Monitor for dynamically loaded content
    const observer = new MutationObserver(function(mutations) {
        mutations.forEach(function(mutation) {
            mutation.addedNodes.forEach(function(node) {
                if (node.nodeType === 1) { // Element node
                    const loginForm = node.querySelector('form');
                    const registerForm = node.querySelector('#registration-form');
                    
                    if (loginForm && loginForm.querySelector('#username, #password')) {
                        setupLoginForm(loginForm);
                    }
                    if (registerForm) {
                        setupRegistrationForm(registerForm);
                    }
                }
            });
        });
    });

    observer.observe(document.body, { childList: true, subtree: true });
});

// Server connection check
async function checkServerConnection() {
    const isHealthy = await apiClient.checkHealth();
    updateServerStatus(isHealthy);
}

function updateServerStatus(isOnline) {
    const statusIndicator = document.getElementById('statusIndicator');
    const statusText = document.getElementById('statusText');
    
    if (statusIndicator && statusText) {
        statusText.textContent = isOnline ? 'Server Online' : 'Server Unreachable';
        statusIndicator.className = `fixed top-20 right-5 z-50 flex items-center px-4 py-2 rounded-lg text-white transition-all duration-500 ${
            isOnline ? 'bg-green-500/90' : 'bg-red-500/90'
        }`;
        
        setTimeout(() => {
            statusIndicator.style.transform = 'translateX(100%)';
        }, 3000);
    }
}

// Setup login form
function setupLoginForm(form) {
    form.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const usernameField = form.querySelector('#username, input[type="text"], input[type="email"]');
        const passwordField = form.querySelector('#password, input[type="password"]');
        const submitButton = form.querySelector('button[type="submit"]');
        
        if (!usernameField || !passwordField) return;
        
        const credentials = {
            username: usernameField.value.trim(),
            password: passwordField.value
        };

        if (!credentials.username || !credentials.password) {
            showAlert('Please fill in all fields', false);
            return;
        }

        // Show loading state
        if (submitButton) {
            submitButton.disabled = true;
            submitButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Logging in...';
        }

        try {
            const result = await apiClient.login(credentials);
            
            if (result.success) {
                showAlert('Login successful! Redirecting...', true);
                setTimeout(() => {
                    window.location.href = 'dashboard.html';
                }, 1500);
            } else {
                showAlert(result.message, false);
            }
        } catch (error) {
            showAlert('Login failed. Please try again.', false);
        } finally {
            if (submitButton) {
                submitButton.disabled = false;
                submitButton.innerHTML = 'Login';
            }
        }
    });
}

// Setup registration form
function setupRegistrationForm(form) {
    form.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const userData = {
            username: form.querySelector('#reg-username')?.value?.trim(),
            email: form.querySelector('#reg-email')?.value?.trim(),
            password: form.querySelector('#reg-password')?.value,
            firstName: form.querySelector('#reg-firstname')?.value?.trim() || 'Student',
            lastName: form.querySelector('#reg-lastname')?.value?.trim() || 'User',
            roles: ['STUDENT']
        };

        if (!userData.username || !userData.email || !userData.password) {
            showAlert('Please fill in all required fields', false);
            return;
        }

        const submitButton = form.querySelector('button[type="submit"]');
        
        // Show loading state
        if (submitButton) {
            submitButton.disabled = true;
            submitButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Registering...';
        }

        try {
            const result = await apiClient.register(userData);
            
            if (result.success) {
                showAlert('Registration successful! Please login.', true);
                setTimeout(() => {
                    window.location.hash = '#login';
                }, 1500);
            } else {
                showAlert(result.message, false);
            }
        } catch (error) {
            showAlert('Registration failed. Please try again.', false);
        } finally {
            if (submitButton) {
                submitButton.disabled = false;
                submitButton.innerHTML = 'Register';
            }
        }
    });
}

// Enhanced alert function
function showAlert(message, isSuccess = false) {
    // Try to find existing alert containers
    let alertContainer = document.getElementById('alertContainer') || 
                        document.getElementById('statusIndicator') ||
                        document.querySelector('.alert-container');
    
    // Create alert container if not found
    if (!alertContainer) {
        alertContainer = document.createElement('div');
        alertContainer.id = 'alertContainer';
        alertContainer.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 9999;
            max-width: 300px;
        `;
        document.body.appendChild(alertContainer);
    }

    // Create alert element
    const alert = document.createElement('div');
    alert.className = `alert alert-${isSuccess ? 'success' : 'danger'} animate__animated animate__fadeInRight`;
    alert.style.cssText = `
        background: ${isSuccess ? '#d4edda' : '#f8d7da'};
        color: ${isSuccess ? '#155724' : '#721c24'};
        border: 1px solid ${isSuccess ? '#c3e6cb' : '#f5c6cb'};
        padding: 12px 16px;
        border-radius: 8px;
        margin-bottom: 10px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        font-size: 14px;
        display: flex;
        align-items: center;
    `;
    
    alert.innerHTML = `
        <i class="fas fa-${isSuccess ? 'check-circle' : 'exclamation-triangle'}" style="margin-right: 8px;"></i>
        ${message}
    `;

    alertContainer.appendChild(alert);

    // Auto remove after 5 seconds
    setTimeout(() => {
        alert.classList.add('animate__fadeOutRight');
        setTimeout(() => {
            if (alert.parentNode) {
                alert.parentNode.removeChild(alert);
            }
        }, 500);
    }, 5000);
}

// Initialize server status check
setTimeout(checkServerConnection, 1000);