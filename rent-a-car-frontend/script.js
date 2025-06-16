document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('rentForm');
    const overlay = document.getElementById('overlay');
    const spinner = document.getElementById('spinner');
    const successModal = document.getElementById('successModal');
    const closeModal = document.getElementById('closeModal');
    
    const showSpinner = () => {
        overlay.style.display = 'block';
        spinner.style.display = 'block';
        document.body.classList.add('modal-open');
    };

    const hideSpinner = () => {
        overlay.style.display = 'none';
        spinner.style.display = 'none';
        document.body.classList.remove('modal-open');
    };

    const showSuccessModal = () => {
        successModal.style.display = 'block';
        overlay.style.display = 'block';
        document.body.classList.add('modal-open');
    };

    const hideSuccessModal = () => {
        successModal.style.display = 'none';
        overlay.style.display = 'none';
        document.body.classList.remove('modal-open');
    };

    closeModal.addEventListener('click', hideSuccessModal);

    // Fechar modal ao clicar no overlay
    overlay.addEventListener('click', (e) => {
        if (e.target === overlay && !spinner.style.display === 'block') {
            hideSuccessModal();
        }
    });

    // Fechar modal com tecla ESC
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && successModal.style.display === 'block') {
            hideSuccessModal();
        }
    });

    const showToast = (message, isError = false) => {
        Toastify({
            text: message,
            duration: 3000,
            gravity: "top",
            position: "right",
            backgroundColor: isError ? "#e74c3c" : "#2ecc71",
            stopOnFocus: true
        }).showToast();
    };

    const validateEmail = (email) => {
        return email.includes('@') && email.includes('.');
    };

    const validateYear = (year) => {
        return year.toString().length === 4 && year >= 1900 && year <= new Date().getFullYear() + 1;
    };

    const validateForm = (formData) => {
        let isValid = true;
        const errors = [];

        // Validar se todos os campos estão preenchidos
        for (const [key, value] of formData.entries()) {
            if (!value) {
                errors.push(`O campo ${key} é obrigatório`);
                document.getElementById(key).classList.add('error', 'shake');
                isValid = false;
            }
        }

        // Validar e-mail
        if (!validateEmail(formData.get('email'))) {
            errors.push('E-mail inválido');
            document.getElementById('email').classList.add('error', 'shake');
            isValid = false;
        }

        // Validar ano
        if (!validateYear(formData.get('ano'))) {
            errors.push('Ano inválido');
            document.getElementById('ano').classList.add('error', 'shake');
            isValid = false;
        }

        errors.forEach(error => showToast(error, true));
        return isValid;
    };

    // Remover classes de erro quando o usuário começar a digitar
    document.querySelectorAll('input, select').forEach(input => {
        input.addEventListener('input', () => {
            input.classList.remove('error', 'shake');
        });
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        document.querySelectorAll('.error').forEach(el => {
            el.classList.remove('error', 'shake');
        });

        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());
        
        if (!validateForm(formData)) {
            return;
        }

        try {
            showSpinner();
            const response = await fetch('https://bff-rent-car-local.blackgrass-cd619821.eastus.azurecontainerapps.io/api/locacao', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });

            hideSpinner();

            if (response.ok) {
                showSuccessModal();
                form.reset();
            } else {
                throw new Error('Erro ao processar a locação');
            }
        } catch (error) {
            hideSpinner();
            showToast('Erro ao enviar os dados: ' + error.message, true);
        }
    });
}); 