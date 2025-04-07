window.addEventListener('load', () => {
    fetch('/students/by-principal')
            .then(response => {
                if(!response.ok) {
                    throw new Error('Ошибка');
                }
                return response.json();
            })
            .then(data => {
                getUserInfo(data.username);
            })
            .catch(error => {
                console.error('Ошибка: ', error);
            });
});

function getUserInfo(email) {
    fetch('/students/by-email?email=' + email)
                .then(response => {
                    if(!response.ok) {
                        throw new Error('Ошибка');
                    }
                    return response.json();
                })
                .then(data => {
                    document.getElementById("name_folder").textContent=data.fullName;
                    document.getElementById("email_folder").textContent=data.email;
                    fillGradesList(data.grades);
                })
                .catch(error => {
                    console.error('Ошибка: ', error);
                });
}

function fillGradesList(grades) {
    const grades_list = document.getElementById("grades");
    grades_list.innerHTML = '';

    grades.forEach(grade => {
        const grade_el = document.createElement('div');
        grade_el.classList.add('grade');

        const grade_content_el = document.createElement('div');
        grade_content_el.classList.add('content');
        grade_el.appendChild(grade_content_el);

        const grade_input_el = document.createElement('input');
        grade_input_el.classList.add('text');
        grade_input_el.type = 'text';
        grade_input_el.value = grade.name;
        grade_input_el.setAttribute('readonly', 'readonly');
        grade_content_el.appendChild(grade_input_el);

        const grade_actions_el = document.createElement('div');
        grade_actions_el.classList.add('actions');

        const grade_date_el = document.createElement('input');
        grade_date_el.classList.add('text');
        grade_date_el.type = 'text';
        grade_date_el.value = grade.startDate;
        grade_date_el.setAttribute('readonly', 'readonly');
        grade_actions_el.appendChild(grade_date_el);

        grades_list.appendChild(grade_el);
    });
}