var API_BASE_URL = "http://localhost:8080";

$.ajaxSetup({
    headers: { 'Authorization': "Basic "}
});

$("#btnRegister").click(function(e){
    register();   
});


function register(){
    $("#result").text('');
    var url = API_BASE_URL + "/hobbylist-api/users/";
    console.log(url);
    var usuario = {username:$("#username").val(), password:$("#password").val(), name:$("#name").val(), email:$("#email").val()};
    var data = JSON.stringify(usuario);
    
    $.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		dataType : 'json',
        contentType: 'application/vnd.hobbylist.api.user+json',
		data : data,
	}).done(function(data, status, jqxhr) {			
       if(data.status == 409)
        {
            $(("#result")).html('<div class="alert alert-danger"> <strong>Oh!</strong> Nombre de usuario y contraseña ya usados </div>');
        }
        else
        {
            window.location="login.html";
        }
    
  	}).fail(function() {
            $(("#result")).html('<div class="alert alert-danger"> <strong>Oh!</strong> Nombre de usuario y contraseña ya usados </div>');
	});
}
