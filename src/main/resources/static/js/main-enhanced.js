// Enhanced Professional ERP System JavaScript

// Global Variables
let particleSystem = null;
let weatherData = null;
let currentUser = null;

// Initialize Everything
document.addEventListener('DOMContentLoaded', function() {
    initializeParticleSystem();
    initializeNavigation();
    initializeAnimations();
    initializeWeather();
    initializeKeyboardShortcuts();
    showWelcomeMessage();
});

// Particle System
function initializeParticleSystem() {
    const particleContainer = document.querySelector('.dynamic-bg');
    if (!particleContainer) return;

    function createParticle() {
        const particle = document.createElement('div');
        particle.className = 'particle';
        particle.style.left = Math.random() * 100 + '%';
        particle.style.width = Math.random() * 6 + 2 + 'px';
        particle.style.height = particle.style.width;
        particle.style.background = `rgba(255, 255, 255, ${Math.random() * 0.5 + 0.2})`;
        particle.style.animationDuration = Math.random() * 8 + 8 + 's';
        particle.style.animationDelay = Math.random() * 2 + 's';
        
        particleContainer.appendChild(particle);
        
        setTimeout(() => {
            particle.remove();
        }, 16000);
    }

    setInterval(createParticle, 800);
}

// Enhanced Navigation System
function initializeNavigation() {
    const navbar = document.getElementById('navbar');
    const navLinks = document.querySelectorAll('.nav-link');
    
    // Scroll effect for navbar
    window.addEventListener('scroll', () => {
        if (window.scrollY > 50) {
            navbar.classList.add('scrolled');
        } else {
            navbar.classList.remove('scrolled');
        }
    });

    // Smooth scrolling for navigation links
    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            const href = this.getAttribute('href');
            if (href && href.startsWith('#')) {
                e.preventDefault();
                const target = document.querySelector(href);
                if (target) {
                    const offsetTop = target.offsetTop - 80;
                    window.scrollTo({
                        top: offsetTop,
                        behavior: 'smooth'
                    });
                }
            }
        });
    });

    // Active link highlighting
    window.addEventListener('scroll', updateActiveNavLink);
}

function updateActiveNavLink() {
    const sections = document.querySelectorAll('section[id]');
    const navLinks = document.querySelectorAll('.nav-link[href^="#"]');
    
    let current = '';
    sections.forEach(section => {
        const sectionTop = section.offsetTop - 100;
        if (window.scrollY >= sectionTop) {
            current = section.getAttribute('id');
        }
    });

    navLinks.forEach(link => {
        link.classList.remove('active');
        if (link.getAttribute('href') === `#${current}`) {
            link.classList.add('active');
        }
    });
}

// Initialize Animations
function initializeAnimations() {
    // Counter animation
    const counters = document.querySelectorAll('.counter');
    const observerOptions = {
        threshold: 0.7
    };

    const counterObserver = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                animateCounter(entry.target);
            }
        });
    }, observerOptions);

    counters.forEach(counter => {
        counterObserver.observe(counter);
    });

    // Floating icons animation
    createFloatingIcons();
}

function animateCounter(element) {
    const target = parseInt(element.getAttribute('data-target'));
    const duration = 2000;
    const step = target / (duration / 16);
    let current = 0;

    const timer = setInterval(() => {
        current += step;
        element.textContent = Math.floor(current).toLocaleString();
        
        if (current >= target) {
            element.textContent = target.toLocaleString();
            clearInterval(timer);
        }
    }, 16);
}

function createFloatingIcons() {
    const icons = ['📚', '🎓', '🏫', '💡', '📊', '🔬', '🎯', '📋'];
    const container = document.querySelector('.dynamic-bg');
    
    if (!container) return;

    icons.forEach((icon, index) => {
        const element = document.createElement('div');
        element.className = `floating-icon animate-float-${['slow', 'medium', 'fast'][index % 3]}`;
        element.textContent = icon;
        element.style.left = Math.random() * 90 + '%';
        element.style.top = Math.random() * 90 + '%';
        element.style.animationDelay = Math.random() * 2 + 's';
        
        container.appendChild(element);
    });
}

// Weather Widget
function initializeWeather() {
    // Simulated weather data - replace with real API
    const weatherWidget = document.querySelector('.weather-widget');
    if (weatherWidget) {
        updateWeatherDisplay();
        setInterval(updateWeatherDisplay, 600000); // Update every 10 minutes
    }
}

function updateWeatherDisplay() {
    // Simulated weather data
    const weatherData = {
        temperature: Math.floor(Math.random() * 15) + 20,
        condition: ['Sunny', 'Partly Cloudy', 'Clear'][Math.floor(Math.random() * 3)],
        humidity: Math.floor(Math.random() * 30) + 40
    };

    const weatherWidget = document.querySelector('.weather-widget');
    if (weatherWidget) {
        weatherWidget.innerHTML = `
            <div class="text-sm font-semibold mb-2">Campus Weather</div>
            <div class="text-2xl font-bold">${weatherData.temperature}°C</div>
            <div class="text-xs opacity-80">${weatherData.condition}</div>
            <div class="text-xs opacity-60 mt-1">Humidity: ${weatherData.humidity}%</div>
        `;
    }
}

// Keyboard Shortcuts
function initializeKeyboardShortcuts() {
    document.addEventListener('keydown', function(e) {
        // ESC to close modals/overlays
        if (e.key === 'Escape') {
            closeAllModals();
        }
        
        // Alt + H for home
        if (e.altKey && e.key === 'h') {
            e.preventDefault();
            document.getElementById('home').scrollIntoView({ behavior: 'smooth' });
        }
    });
}

// Enhanced Modal Functions
function closeAllModals() {
    document.querySelectorAll('.modal-overlay').forEach(modal => {
        modal.classList.add('hidden');
    });
}

// Virtual Tour Function
function startVirtualTour() {
    showAlert('statusIndicator', '🎥 Virtual tour coming soon! Experience our campus in 360°', true);
}

// Event Registration
function registerForEvent(eventId) {
    showAlert('statusIndicator', '✅ Successfully registered for the event!', true);
}

// ChatBot Functions
function toggleChatBot() {
    showAlert('statusIndicator', '🤖 AI Assistant is being activated...', true);
    setTimeout(() => {
        showAlert('statusIndicator', '💬 Hello! How can I help you today?', true);
    }, 1500);
}

// Notification Functions
function showNotifications() {
    const notifications = [
        '📢 New assignment posted in Mathematics',
        '🎯 Exam schedule updated',
        '📚 Library hours extended for finals week'
    ];
    
    const randomNotification = notifications[Math.floor(Math.random() * notifications.length)];
    showAlert('statusIndicator', randomNotification, true);
}

// Enhanced Alert System
function showAlert(elementId, message, isSuccess = false) {
    const indicator = document.getElementById(elementId);
    if (!indicator) return;

    const statusText = indicator.querySelector('#statusText') || indicator.querySelector('span');
    if (statusText) {
        statusText.textContent = message;
    } else {
        indicator.textContent = message;
    }
    
    indicator.className = `fixed top-20 right-5 z-50 flex items-center px-4 py-2 rounded-lg text-white transition-all duration-500 transform ${
        isSuccess ? 'bg-green-500/90' : 'bg-red-500/90'
    }`;
    
    indicator.style.transform = 'translateX(0)';
    
    setTimeout(() => {
        indicator.style.transform = 'translateX(100%)';
    }, 3000);
}

// Quick Login Function
function quickLogin() {
    showAlert('statusIndicator', '🔐 Login system activated - redirecting...', true);
    setTimeout(() => {
        window.location.href = '/login';
    }, 1500);
}

// Welcome Message
function showWelcomeMessage() {
    setTimeout(() => {
        showAlert('statusIndicator', '🎓 Welcome to Smart ERP System', true);
    }, 1000);
}

// Mobile Menu Toggle
function toggleMobileMenu() {
    const mobileMenu = document.getElementById('mobileMenu');
    if (mobileMenu) {
        mobileMenu.classList.toggle('hidden');
    }
}

// Mega Menu Functions
function showMegaMenu(menuId) {
    const menu = document.getElementById(menuId);
    if (menu) {
        menu.classList.add('active');
    }
}

function hideMegaMenu(menuId) {
    const menu = document.getElementById(menuId);
    if (menu) {
        menu.classList.remove('active');
    }
}

// Accessibility Enhancements
function handleKeyNavigation(event) {
    if (event.key === 'Tab') {
        // Ensure proper tab navigation
        const focusableElements = document.querySelectorAll(
            'a[href], button, textarea, input[type="text"], input[type="radio"], input[type="checkbox"], select'
        );
        
        if (event.shiftKey) {
            // Shift + Tab (backwards)
            if (document.activeElement === focusableElements[0]) {
                event.preventDefault();
                focusableElements[focusableElements.length - 1].focus();
            }
        } else {
            // Tab (forwards)
            if (document.activeElement === focusableElements[focusableElements.length - 1]) {
                event.preventDefault();
                focusableElements[0].focus();
            }
        }
    }
}

document.addEventListener('keydown', handleKeyNavigation);

// Performance Optimizations
function optimizePerformance() {
    // Reduce animations on low-end devices
    if (navigator.hardwareConcurrency <= 2) {
        document.body.classList.add('reduced-motion');
    }
    
    // Lazy load images
    const images = document.querySelectorAll('img[data-src]');
    const imageObserver = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const img = entry.target;
                img.src = img.dataset.src;
                img.removeAttribute('data-src');
                imageObserver.unobserve(img);
            }
        });
    });
    
    images.forEach(img => imageObserver.observe(img));
}

// Initialize performance optimizations
document.addEventListener('DOMContentLoaded', optimizePerformance);
