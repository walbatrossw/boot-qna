// 답변하기 버튼 클릭 이벤트 발생시 addAnswer 함수 호출
$(".answer-write input[type=submit]").click(addAnswer);

// 답변하기 추가 AJAX 요청 처리를 위한 함수
function addAnswer(e) {
    console.log("clicked");
    e.preventDefault(); // submit 기본 효과 방지
    var queryString = $(".answer-write").serialize(); // JSON 타입으로 객체 직렬화
    console.log("queryString : "+queryString);
    var url = $(".answer-write").attr("action"); // 요청 URL 저장
    console.log("url : "+url);
    // AJAX POST 요청 처리
    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess
    });
}

// AJAX 요청 처리 실패시
function onError(status) {
    console.log("error" + status);
}

// AJAX 요청 처리 성공시
function onSuccess (data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template); //  변수 template 에 담긴 답변을 append
    $(".answer-write textarea").val("");    // textarea 에 남아있는 내용 지우기
}

// 답변삭제 클릭 이벤트 발생시 deleteAnswer 함수 호출
$(".qna-comment").on('click', '.link-delete-article', deleteAnswer);
// 답변삭제 AJAX 요청 처리를 위한 함수
function deleteAnswer(e) {
    e.preventDefault(); // a태그 기본효과 방지
    var deleteBtn = $(this);
    var url = deleteBtn.attr("href");
    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : function (xhr, status) {
            console.log("error");
        },
        success : function (data, status) {
            console.log(data);
            if (data.valid) {
                deleteBtn.closest("article").remove();
            } else {
                alert(data.errorMsg);
            }
        }
    });
}

// 답변 템플릿 : replace() 함수와 정규표현식을 통해 변환
String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};