var API_BASE_URL = "http://localhost:8080";
//var USERNAME = "";
//var PASSWORD = "";

// $.ajaxSetup({
// headers : {
// 'Authorization' : "Basic " + btoa(USERNAME + ':' + PASSWORD)
// }
// });



$("#btn_Buscar").click(function(e) {
	e.preventDefault();
    BuscarLista($("classification").val());
    BuscarLista($("director").val());
    BuscarLista($("author").val());
    BuscarLista($("company").val());
    BuscarLista($("genre").val());
    BuscarLista($("title").val());
    BuscarLista($("year").val());

	
});

function BuscarLista(classification, director, author, company, genre, title, year) {
	classificaction = $("#classification").val();
    director = $("#director").val();
    author = $("#author").val();
    company = $("#company").val();
    genre = $("#genre").val();
    title = $("#title").val();
    year = $("#year").val();
    
	var url = API_BASE_URL + '/hobbylist-api/hobbies/search/?classification=' + classification;
	$("#result_Buscar_M").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

				var hobbie = data;
        
                    if (hobbie.classification =="Movie"){
					$('<strong> Title: ' + hobbie.title + '<br>').appendTo($('#result_Buscar_M'));
					$('<strong> Director: ' + hobbie.director + '<br>').appendTo($('#result_Buscar_M'));
					$('<strong> Género: ' + hobbie.genre + '<br>').appendTo($('#result_Buscar_M'));
                    $('<strong> Año: ' + hobbie.year + '<br>').appendTo($('#result_Buscar_M'));
					$('<strong> Sinopsis: </strong> ' + hobbie.synopsis + '<br>').appendTo($('#result_Buscar_M'));
                    $('<strong> ID: ' + hobbie.hobbyid + '<br>').appendTo($('#result_Buscar_M'));
                    $('<br>').appendTo($('#result_Buscar_M'));
                    
                    }
                    else if (hobbie.classification == "Book")
                    {
                    $('<strong> Title: ' + hobbie.title + '<br>').appendTo($('#result_Buscar_B'));
					$('<strong> Autor: ' + hobbie.author + '<br>').appendTo($('#result_Buscar_B'));
					$('<strong> Género: ' + hobbie.genre + '<br>').appendTo($('#result_Buscar_B'));
                    $('<strong> Año: ' + hobbie.year + '<br>').appendTo($('#result_Buscar_B'));
					$('<strong> Sinopsis: </strong> ' + hobbie.synopsis + '<br>').appendTo($('#result_Buscar_B'));
                    $('<strong> ID: ' + hobbie.hobbyid + '<br>').appendTo($('#result_Buscar_B'));
                    $('<br>').appendTo($('#result_Buscar_B'));
                    }
                    else if (hobbie.classification == "Game")
                    {
                    $('<strong> Title: ' + hobbie.title + '<br>').appendTo($('#result_Buscar_V'));
					$('<strong> Compañía: ' + hobbie.company + '<br>').appendTo($('#result_Buscar_V'));
					$('<strong> Género: ' + hobbie.genre + '<br>').appendTo($('#result_Buscar_V'));
                    $('<strong> Año: ' + hobbie.year + '<br>').appendTo($('#result_Buscar_V'));
					$('<strong> Sinopsis: </strong> ' + hobbie.synopsis + '<br>').appendTo($('#result_Buscar_V'));
                    $('<strong> ID: ' + hobbie.hobbyid + '<br>').appendTo($('#result_Buscar_V'));
                    $('<br>').appendTo($('#result_Buscar_V'));
                        
                    }
			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> Búsqueda en Cine no encontrada </div>').appendTo($("#result_Buscar_M"));
                $('<div class="alert alert-danger"> <strong>Oh!</strong> Búsqueda en Libro no encontrada </div>').appendTo($("#result_Buscar_B"));
                $('<div class="alert alert-danger"> <strong>Oh!</strong> Búsqueda en Videojuego no encontrada </div>').appendTo($("#result_Buscar_V"));
	});

}
