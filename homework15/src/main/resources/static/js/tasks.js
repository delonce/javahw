var role = 'Anonymous';

window.addEventListener('load', () => {
    fetch('/api/v1/users/info')
            .then(response => {
                if(!response.ok) {
                    throw new Error('Ошибка');
                }
                return response.json();
            })
            .then(data => {
                getUserInfo(data.username);
                getAccessToAddingTask();
            })
            .catch(error => {
                console.error('Ошибка: ', error);
            });

    reload_tasks();
});


function getAccessToAddingTask() {
    document.getElementById('add_task').addEventListener('click', (e) => {
                var task_name = document.getElementById('name_input').value;
                var task_description = document.getElementById('description_input').value;

                const new_task = {
                    name: task_name,
                    description: task_description
                };

                if (task_name && task_description) {
                    fetch('/api/v1/tasks', {
                        method: 'POST',
                        headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(new_task)
                    })
                    .then(response => {
                        if(!response.ok) {
                            throw new Error('Ошибка');
                        }
                        return response.json();
                    })
                    .then(data => {
                        reload_tasks();
                    })
                    .catch(error => {
                        console.error('Ошибка: ', error);
                    });
                }
                else {
                    alert('There are empty placeholders!');
                }
    });
}

function getUserInfo(username) {
    document.getElementById("name_folder").textContent='Name: ' + username;

    fetch('/api/v1/users/role')
                .then(response => {
                    if(!response.ok) {
                        throw new Error('Ошибка');
                    }
                    return response.text();
                })
                .then(data => {
                    document.getElementById("role_folder").textContent='Role: ' + data;
                    role = data;

                    if (role != 'ROLE_USER' && role != 'ROLE_ADMIN') {
                        document.getElementById('new_task_form').innerHTML = ''
                    }
                })
                .catch(error => {
                    console.error('Ошибка: ', error);
                });
}

function reload_tasks() {
    fetch('api/v1/tasks')
            .then(response => {
                if(!response.ok) {
                    throw new Error('Ошибка');
                }

                return response.json();
            })
            .then(data => fillTasksList(data))
            .catch(error => {
                console.error('Ошибка: ', error);
            });
}

function fillTasksList(tasks) {
    const tasks_list = document.getElementById("tasks");
    tasks_list.innerHTML = '';

    tasks.forEach(task => {
        const task_el = document.createElement('div');
        task_el.classList.add('task');

        const task_content_el = document.createElement('div');
        task_content_el.classList.add('content');
        task_el.appendChild(task_content_el);

        const task_input_el = document.createElement('input');
        task_input_el.classList.add('text');
        task_input_el.type = 'text';
        task_input_el.value = task.name;
        task_input_el.setAttribute('readonly', 'readonly');
        task_content_el.appendChild(task_input_el);

        const task_actions_el = document.createElement('div');
        task_actions_el.classList.add('actions');

        const task_date_el = document.createElement('input');
        task_date_el.classList.add('text');
        task_date_el.type = 'text';
        task_date_el.value = task.createdAt;
        task_date_el.setAttribute('readonly', 'readonly');
        task_actions_el.appendChild(task_date_el);

        if (role == 'ROLE_ADMIN') {
            const task_delete_el = document.createElement('button');
            task_delete_el.classList.add('delete');
            task_delete_el.innerText = 'Delete';
            task_actions_el.appendChild(task_delete_el);

            task_delete_el.addEventListener('click', (e) => {
                fetch('/api/v1/tasks/' + task.id, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (response.status == 200) {
                        return 'ok';
                    }
                    else {
                        throw new Error('Could not create a room!');
                    }
                })
                .then(data => reload_tasks())
                .catch(error => alert(error));
            });
        }

        task_el.appendChild(task_actions_el);

        tasks_list.appendChild(task_el);
    });
}