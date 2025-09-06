document.addEventListener('DOMContentLoaded', function() {
    AOS.init();

    const mainContent = document.getElementById('main-content');
    const navbarContainer = document.getElementById('navbar-container');
    const footerContainer = document.getElementById('footer-container');

    const routes = {
        '#home': 'partials/home.html',
        '#login': 'partials/login.html',
        '#register': 'partials/register.html',
        '#about': 'partials/about.html',
        '#courses': 'partials/courses.html',
        '#library': 'partials/library.html',
        '#placement': 'partials/placement.html',
        '#contact': 'partials/contact.html'
    };

    function loadContent(url) {
        fetch(url)
            .then(response => response.text())
            .then(data => {
                mainContent.innerHTML = data;
                if (url.includes('login')) {
                    setupLogin();
                }
                if (url.includes('register')) {
                    setupRegistration();
                }
            });
    }

    function loadPartial(container, url) {
        fetch(url)
            .then(response => response.text())
            .then(data => {
                container.innerHTML = data;
            });
    }

    function router() {
        const hash = window.location.hash || '#home';
        const path = routes[hash];
        if (path) {
            loadContent(path);
        } else {
            loadContent(routes['#home']);
        }
    }

    window.addEventListener('hashchange', router);
    
    loadPartial(navbarContainer, 'partials/navbar.html');
    loadPartial(footerContainer, 'partials/footer.html');
    router();

    function setupLogin() {
        const togglePassword = document.querySelector('#togglePassword');
        const password = document.querySelector('#password');

        if (togglePassword) {
            togglePassword.addEventListener('click', function (e) {
                const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
                password.setAttribute('type', type);
                this.classList.toggle('fa-eye-slash');
            });
        }
    }

    function setupRegistration() {
        const nextButtons = document.querySelectorAll('.next-step');
        const prevButtons = document.querySelectorAll('.prev-step');
        const formSteps = document.querySelectorAll('.form-step');
        const progressBar = document.querySelector('.progress-bar');

        let currentStep = 0;

        nextButtons.forEach(button => {
            button.addEventListener('click', () => {
                currentStep++;
                updateFormSteps();
            });
        });

        prevButtons.forEach(button => {
            button.addEventListener('click', () => {
                currentStep--;
                updateFormSteps();
            });
        });

        function updateFormSteps() {
            formSteps.forEach((step, index) => {
                step.classList.toggle('active', index === currentStep);
            });
            const progress = ((currentStep + 1) / formSteps.length) * 100;
            progressBar.style.width = `${progress}%`;
        }
    }
});
