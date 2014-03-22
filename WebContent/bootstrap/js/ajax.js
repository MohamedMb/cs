/*------------------------------------------------------------  
									AJAX
		------------------------------------------------------------*/
		function Ajax() {
		   xhr = null ;
		   if (window.XMLHttpRequest) {
			  xhr = new XMLHttpRequest() ;  
		   }else{
			  if (window.ActiveXObject) {
				 xhr = new ActiveXObject("Microsoft.XMLHTTP") ;
			  }else{
				 alert("Votre navigateur n'est pas apte à gérer Ajax") ;
			  }
		   }
		   return xhr ;
		}

		function AjaxPost(nomfic, message) {
		   xhr = Ajax() ;
		   if (xhr) {                              
			  xhr.open("POST", nomfic, true) ;
			  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded") ;   
			  xhr.send(message) ;
		   }
		}                

		function AjaxGet(nomfic, typefic, uneFonction) { 
		   xhr = Ajax() ;
		   if (xhr) {
			  xhr.onreadystatechange = function () {
				 if (xhr.readyState == 4 && xhr.status == 200) {
					if (typefic == "XML") {
					   uneFonction(xhr.responseXML) ;
					}else{
					   uneFonction(xhr.responseText) ;
					}
				 }    
			  }
			  xhr.open("GET", nomfic, true) ;
			  xhr.send(null) ;
		   }
		}