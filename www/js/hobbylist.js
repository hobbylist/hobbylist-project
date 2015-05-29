var API_BASE_URL = "http://localhost:8080";

$.ajaxSetup({
    headers: { 'Authorization': "Basic "}
});

$(document).ready(function()
/**$("#button_list").click(function(e)**/{
	/**e.preventDefault();**/
    var url = API_BASE_URL + '/hobbylist-api/hobbies';
	getList(url);
    /**console.log("Entro al boton");**/
});


function getList(url) {
	$("#result").text('');
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
				var hobbies = data.hobbies;
				
				$.each(hobbies, function(i, v) {
					var hobbie = v;
                    if (hobbie.classification =="Movie"){
					$('<strong> Title: ' + hobbie.title + '<br>').appendTo($('#result'));
					$('<strong> Director: ' + hobbie.director + '<br>').appendTo($('#result'));
					$('<strong> Género: ' + hobbie.genre + '<br>').appendTo($('#result'));
                    $('<strong> Año: ' + hobbie.year + '<br>').appendTo($('#result'));
					$('<strong> Sinopsis: </strong> ' + hobbie.synopsis + '<br>').appendTo($('#result'));
                    $('<br>').appendTo($('#result'));
                    console.log(hobbie);
                    }
                    else if (hobbie.classification == "Book")
                    {
                    $('<strong> Title: ' + hobbie.title + '<br>').appendTo($('#result_Book'));
					$('<strong> Autor: ' + hobbie.author + '<br>').appendTo($('#result_Book'));
					$('<strong> Género: ' + hobbie.genre + '<br>').appendTo($('#result_Book'));
                    $('<strong> Año: ' + hobbie.year + '<br>').appendTo($('#result_Book'));
					$('<strong> Sinopsis: </strong> ' + hobbie.synopsis + '<br>').appendTo($('#result_Book'));
                    $('<br>').appendTo($('#result_Book'));
                    }
                    else if (hobbie.classification == "Game")
                    {
                    $('<strong> Title: ' + hobbie.title + '<br>').appendTo($('#result_Game'));
					$('<strong> Compañía: ' + hobbie.company + '<br>').appendTo($('#result_Game'));
					$('<strong> Género: ' + hobbie.genre + '<br>').appendTo($('#result_Game'));
                    $('<strong> Año: ' + hobbie.year + '<br>').appendTo($('#result_Game'));
					$('<strong> Sinopsis: </strong> ' + hobbie.synopsis + '<br>').appendTo($('#result_Game'));
                    $('<br>').appendTo($('#result_Game'));
                        
                    }
				});
				

	}).fail(function() {
		$("#result").text("No files.");
        $("#result_Book").text("No files.");
        $("#result_Game").text("No files.");
	});

}


