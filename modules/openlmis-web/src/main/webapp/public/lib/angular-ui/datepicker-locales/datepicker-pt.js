/* Portuguese initialisation for the jQuery UI date picker plugin. */
( function( factory ) {
	if ( typeof define === "function" && define.amd ) {

		// AMD. Register as an anonymous module.
		define( [ "../widgets/datepicker" ], factory );
	} else {

		// Browser globals
		factory( jQuery.datepicker );
	}
}( function( datepicker ) {

datepicker.regional.pt = {
	closeText: "Fechar",
	prevText: "Ante",
	nextText: "Prox",
	currentText: "Hoje",
	monthNames: [ "Janeiro","Fevereiro","Março","Abril","Maio","Junho",
	"Julho","Agosto","Setembro","Outubro","Novembro","Dezembro" ],
	monthNamesShort: [ "Jan","Fev","Mar","Abr","Mai","Jun",
	"Jul","Ago","Set","Out","Nov","Dez" ],
	dayNames: [
		"Domingo",
		"Segunda-feira",
		"Terça-feira",
		"Quarta-feira",
		"Quinta-feira",
		"Sexta-feira",
		"Sábado"
	],
	dayNamesShort: [ "Dom","Seg","Ter","Qua","Qui","Sex","Sáb" ],
	dayNamesMin: [ "Dom","Seg","Ter","Qua","Qui","Sex","Sáb" ],
	weekHeader: "Sem",
	firstDay: 0,
	isRTL: false,
	showMonthAfterYear: false,
	yearSuffix: "" };

return datepicker.regional.pt;

} ) );
