 function checkToken(){
 var token = $.session.get('token');
                if (token===undefined)
                {
                    document.location.href="../login.html";
                }
 }