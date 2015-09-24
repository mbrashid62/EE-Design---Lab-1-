/**
 * Created by rshid on 9/21/2015.
 */
/**
 * Created by rshid on 9/20/2015.
 */

var chart;
var myEl = $('#chart-div');

myEl.css('border', '1px red solid');

var getTempJSON = function(){

    var response_JSON = [];

    $.ajax({
        url: 'temps/temps.json',
        async: false,
        dataType: 'json',
        success: function(response){
            response_JSON = response;
        }
    });
    return response_JSON;
};

var createChart = function (){

    $('#chart-div').remove();
    $("#parent-div").append("<div id = 'chart-div' style= 'height: 450px;'></div>");

    chart = new Morris.Line({
        // ID of the element in which to draw the chart.
        element: 'chart-div',
        // Chart data records -- each entry in this array corresponds to a point on
        data: getTempJSON(),
        // The name of the data record attribute that contains x-values.
        xkey: 'seconds',
        // A list of names of data record attributes that contain y-values.
        ykeys: ['temperature'],
        // Labels for the ykeys -- will be displayed when you hover over the
        // chart.
        ymax: 50,
        ymin: 10,
        xLabels: "",
        xLabelAngle: 60,
        labels: ['temperature'],


        continuousLine: true
    });
};


window.setInterval(function(){
    createChart();
}, 1000);

