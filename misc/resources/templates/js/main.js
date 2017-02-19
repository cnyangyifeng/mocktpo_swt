$(function () {
    $("#title").text(getTitle());
    $("#startTime").text(getStartTime());
    $("#endTime").text(getEndTime());

    $("#totalQuestionCountInReadingSection").text(getTotalQuestionCountInSection(1));
    $("#totalQuestionCountInListeningSection").text(getTotalQuestionCountInSection(2));

    var readingDetails = jQuery.parseJSON(getQuestionAndAnswerDetails(1));
    $.each(readingDetails, function (index, value) {
        $("#readingDetailsTableBody").append("<tr class=\"success\"><td>" + value.questionNumberInSection + "</td><td>" + value.question + "</td><td>" + value.answer + "</td><td>A</td><td><a class=\"btn btn-default\" href=\"#\">View / Take Note</a></td></tr>");
    });

    var listeningDetails = jQuery.parseJSON(getQuestionAndAnswerDetails(2));
    $.each(listeningDetails, function (index, value) {
        $("#listeningDetailsTableBody").append("<tr class=\"success\"><td>" + value.questionNumberInSection + "</td><td>" + value.question + "</td><td>" + value.answer + "</td><td>A</td><td><a class=\"btn btn-default\" href=\"#\">View / Take Note</a></td></tr>");
    });

    var speakingDetails = jQuery.parseJSON(getQuestionAndAnswerDetails(3));
    $.each(speakingDetails, function (index, value) {
        $("#speakingDetailsTableBody").append("<tr class=\"success\"><td>" + value.questionNumberInSection + "</td><td>" + value.question + "</td><td>" + value.answer + "</td><td>A</td><td><a class=\"btn btn-default\" href=\"#\">View / Take Note</a></td></tr>");
    });

    var writingDetails = jQuery.parseJSON(getQuestionAndAnswerDetails(4));
    $.each(writingDetails, function (index, value) {
        $("#writingDetailsTableBody").append("<tr class=\"success\"><td>" + value.questionNumberInSection + "</td><td>" + value.question + "</td><td>" + value.answer + "</td><td>A</td><td><a class=\"btn btn-default\" href=\"#\">View / Take Note</a></td></tr>");
    });
});