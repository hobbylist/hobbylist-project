var API_BASE_URL = "http://localhost:8080";
var username = $.cookie('username');

$.ajaxSetup({
    headers: { 'Authorization': "Basic "}
});

$(document).ready(function()
/**$("#button_list").click(function(e)**/{
	/**e.preventDefault();**/
    var url = API_BASE_URL + '/hobbylist-api/hobbies/' + username;
	getList(url);
    /**console.log("Entro al boton");**/
});

/**$("#b-searchbtn").click(function(e) {
	e.preventDefault();
	getList($("#title").val());
});**/


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
                    $('<strong> ID: ' + hobbie.hobbyid + '<br>').appendTo($('#result'));
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
                    $('<strong> ID: ' + hobbie.hobbyid + '<br>').appendTo($('#result_Book'));
                    $('<br>').appendTo($('#result_Book'));
                    }
                    else if (hobbie.classification == "Game")
                    {
                    $('<strong> Title: ' + hobbie.title + '<br>').appendTo($('#result_Game'));
					$('<strong> Compañía: ' + hobbie.company + '<br>').appendTo($('#result_Game'));
					$('<strong> Género: ' + hobbie.genre + '<br>').appendTo($('#result_Game'));
                    $('<strong> Año: ' + hobbie.year + '<br>').appendTo($('#result_Game'));
					$('<strong> Sinopsis: </strong> ' + hobbie.synopsis + '<br>').appendTo($('#result_Game'));
                    $('<strong> ID: ' + hobbie.hobbyid + '<br>').appendTo($('#result_Game'));
                    $('<br>').appendTo($('#result_Game'));
                        
                    }
				});
				

	}).fail(function() {
		$("#result").text("No files.");
        $("#result_Book").text("No files.");
        $("#result_Game").text("No files.");
	});
}


/**function getList(title) {
	var url = API_BASE_URL + '/hobbylist-api/hobbies/' + title;
	$("#result_title_Movie").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

		var hobbie = data;
        if (hobbie.classification =="Movie"){
		$("#result_title_Movie").text('');
		$('<strong> Title: ' + hobbie.title + '<br>').appendTo($('#result_title_Movie'));
        $('<strong> Director: ' + hobbie.director + '<br>').appendTo($('#result_title_Movie'));
        $('<strong> Género: ' + hobbie.genre + '<br>').appendTo($('#result_title_Movie'));
        $('<strong> Año: ' + hobbie.year + '<br>').appendTo($('#result_title_Movie'));
        $('<strong> Sinopsis: </strong> ' + hobbie.synopsis + '<br>').appendTo($('#result_title_Movie'));
        $('<strong> ID: ' + hobbie.hobbyid + '<br>').appendTo($('#result_title_Movie'));
        $('<br>').appendTo($('#result_title_Movie'));
        }
        else if (hobbie.classification == "Book")
        {
        $('<strong> Title: ' + hobbie.title + '<br>').appendTo($('#result_title_Book'));
        $('<strong> Autor: ' + hobbie.author + '<br>').appendTo($('#result_title_Book'));
        $('<strong> Género: ' + hobbie.genre + '<br>').appendTo($('#result_title_Book'));
        $('<strong> Año: ' + hobbie.year + '<br>').appendTo($('#result_title_Book'));
        $('<strong> Sinopsis: </strong> ' + hobbie.synopsis + '<br>').appendTo($('#result_title_Book'));
        $('<strong> ID: ' + hobbie.hobbyid + '<br>').appendTo($('#result_title_Book'));
        $('<br>').appendTo($('#result_title_Book'));
        }
        else if (hobbie.classification == "Game")
        {
        $('<strong> Title: ' + hobbie.title + '<br>').appendTo($('#result_title_Game'));
        $('<strong> Compañía: ' + hobbie.company + '<br>').appendTo($('#result_title_Game'));
        $('<strong> Género: ' + hobbie.genre + '<br>').appendTo($('#result_title_Game'));
        $('<strong> Año: ' + hobbie.year + '<br>').appendTo($('#result_title_Game'));
        $('<strong> Sinopsis: </strong> ' + hobbie.synopsis + '<br>').appendTo($('#result_title_Game'));
        $('<strong> ID: ' + hobbie.hobbyid + '<br>').appendTo($('#result_title_Game'));
        $('<br>').appendTo($('#result_title_Game'));    
        }
        
            
	}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> Película no encontrada </div>').appendTo($("#result_title_Movie"));
                $('<div class="alert alert-danger"> <strong>Oh!</strong> Libro no encontrado </div>').appendTo($("#result_title_Book"));
                $('<div class="alert alert-danger"> <strong>Oh!</strong> Juego no encontrado </div>').appendTo($("#result_title_Game"));
	});

}**/

   


