window.onload = function () {
    getAllTrainingPrograms();
};

function getAllTrainingPrograms() {
    //Get JSON data by calling action method in controller
    fetch('http://localhost:8080/fetch/training-programs-all')
        .then(response => response.json())
.then(data => {
        $.each(data, function (i, value) {
            console.log(value);
        })
    });
}