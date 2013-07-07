/*
$(document).ready(function(){


$('.carousel').carousel();

});*/

$(document).ready(function() {

//Switch steps in a tutorial
  $('#stepTab a').click(function (e) {  
  e.preventDefault();
  $(this).tab('show');
})

//display dates for creating plans
$(function() {
    $("#txtStartDate").datepicker();
  });

$(function() {
    $("#txtEndDate").datepicker();
  });

});




function bmiCalculate(){
 var height=document.getElementById("txtHeight").value;
 var weight=document.getElementById("txtWeight").value;
 var bmi= weight/(height*height);
 var result="BMI Value: "+bmi;
 if(height!='')
 document.getElementById("bmiResult").innerHTML=result; 
	document.getElementById("hiddenBMI").value = bmi;
	document.getElementById("hiddenHeight").value = height;
	document.getElementById("hiddenWeight").value = weight;
}

function populateDay(){
 //{sept, april, june and nov = 30 days};
 //{feb=28, 29 for leap year}
 //others= 31
 var month=document.getElementById("selMonth").value;
 var year=document.getElementById("selYear").value;
 var day;
 if((year % 4)==0){
   if(month==2) day=29;
	}
 else{
     if(month==2) day=28;
     else if(month==4 || month==6 || month==9 || month==11) day=30;
     else day=31;
  }

  var selDay=document.getElementById("selDay");
  var iNumbr=selDay.childNodes.length; 
  
  if(day<iNumbr){
    //remove def # of elements
        var len=selDay.childNodes.length;
  	for(i=iNumbr-1; i>=day; --i)
	{
	 selDay.removeChild(selDay.childNodes[i]);
	}
  }
 else if(day>iNumbr){
 //add deffresh ones
  for(i=iNumbr+1; i<=day; ++i)
   {
	var opt=document.createElement("option");
 	opt.label=i;
	opt.value=i;
        selDay.appendChild(opt);
   }

  } 
}

function populateDOBFields(){
	var parent=document.getElementById("selYear");	
	for(i=1945; i<=2000; ++i)
	{
	var opt=document.createElement("option");	
	opt.label=i;
	opt.value=i;
	parent.appendChild(opt);
	}

	var parent=document.getElementById("selDay");	
	for(i=1; i<=31; ++i)
	{
	var opt=document.createElement("option");	
	opt.label=i;
	opt.value=i;
	parent.appendChild(opt);
	}
}
