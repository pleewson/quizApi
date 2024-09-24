fetch('/api/questions')
    .then(response => response.json())
    .then(data => {
        const questionContainer = document.getElementById('question-container');

        //question
        const questionElement = document.createElement('p'); //tworze <p> dla question
        questionElement.textContent = data.question;
        questionElement.id = data.id;
        questionContainer.appendChild(questionElement);

        //ul
        const answerList = document.createElement('ul');
        data.answers.forEach(answerData => {
            //li
            const answerElement = document.createElement('li');

            //checkbox
            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.id = answerData.id;

            //label
            const label = document.createElement('label');
            label.htmlFor = checkbox.id;
            label.textContent = answerData.answer;

            //checbox && label -> li -> ul
            answerElement.appendChild(checkbox);
            answerElement.appendChild(label);

            answerList.appendChild(answerElement);
        })

        //list -> container
        questionContainer.appendChild(answerList);

        //adding checked boxed into array
        const submitButton = document.getElementById('submit-answers');
        submitButton.addEventListener('click', () => {
            const selectedAnswers = [];

            data.answers.forEach(answerData => {
                const checkbox = document.getElementById(answerData.id);
                if (checkbox.checked) {
                    selectedAnswers.push(checkbox.id);
                }
            })

            //Object with answers
            const payload = {
                answers: selectedAnswers,
                questionId: questionElement.id
            }

            //sending JSON to backend
            fetch('/api/answers', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            }).then(response => {
                return response.json();
            }).then(data => {
                console.log('respond from backend ', data)

                //fill checkAnswer element with answer
                const checkAnswer = document.getElementById('check-answer');
                if (data.correct === true) {
                    checkAnswer.textContent = "Congratulations your answer was correct:)";
                } else {
                    checkAnswer.textContent = "Something went wrong, try again";
                }
            })
                .catch(error => console.log('error', error))
        })

    })

