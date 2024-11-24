function test() {
    var tabsNewAnim = $("#navbarSupportedContent");
    var selectorNewAnim = $("#navbarSupportedContent").find("li").length;
    var activeItemNewAnim = tabsNewAnim.find(".active");
    var activeWidthNewAnimHeight = activeItemNewAnim.innerHeight();
    var activeWidthNewAnimWidth = activeItemNewAnim.innerWidth();
    var itemPosNewAnimTop = activeItemNewAnim.position();
    var itemPosNewAnimLeft = activeItemNewAnim.position();
    $(".hori-selector").css({
        top: itemPosNewAnimTop.top + "px",
        left: itemPosNewAnimLeft.left + "px",
        height: activeWidthNewAnimHeight + "px",
        width: activeWidthNewAnimWidth + "px"
    });
    $("#navbarSupportedContent").on("click", "li", function (e) {
        $("#navbarSupportedContent ul li").removeClass("active");
        $(this).addClass("active");
        var activeWidthNewAnimHeight = $(this).innerHeight();
        var activeWidthNewAnimWidth = $(this).innerWidth();
        var itemPosNewAnimTop = $(this).position();
        var itemPosNewAnimLeft = $(this).position();
        $(".hori-selector").css({
            top: itemPosNewAnimTop.top + "px",
            left: itemPosNewAnimLeft.left + "px",
            height: activeWidthNewAnimHeight + "px",
            width: activeWidthNewAnimWidth + "px"
        });
    });
}
$(document).ready(function () {
    setTimeout(function () {
        test();
    });
});
$(window).on("resize", function () {
    setTimeout(function () {
        test();
    }, 500);
});
$(".navbar-toggler").click(function () {
    $(".navbar-collapse").slideToggle(300);
    setTimeout(function () {
        test();
    });
});

// --------------add active class-on another-page move----------
jQuery(document).ready(function ($) {
    // Get current path and find target link
    var path = window.location.pathname.split("/").pop();

    // Account for home page with empty path
    if (path == "") {
        path = "index.html";
    }

    var target = $('#navbarSupportedContent ul li a[th:href="@{' + path + '}"]');
    // Add active class to target link
    target.parent().addClass("active");
});

// jQuery(document).ready(function ($) {
//     // 현재 페이지를 식별하기 위한 page 변수를 사용
//     var currentPage = $('body').data('page');
//
//     // 페이지가 로드되었을 때 현재 페이지에 맞는 네비게이션 항목에 active 클래스를 추가
//     $('#navbarSupportedContent ul li a').each(function () {
//         var page = $(this).data('page');
//         if (page === currentPage) {
//             $(this).parent().addClass("active");
//         }
//     });
//
//     // 네비게이션 항목 클릭 이벤트 처리 (AJAX로 콘텐츠 변경)
//     $('#navbarSupportedContent ul li a').click(function (e) {
//         e.preventDefault(); // 기본 링크 동작 방지
//
//         var page = $(this).data('page'); // 클릭된 링크의 data-page 값 가져오기
//
//         // 네비게이션 항목의 active 클래스 업데이트
//         $('#navbarSupportedContent ul li').removeClass("active");
//         $(this).parent().addClass("active");
//
//         // 콘텐츠 영역 업데이트
//         $.ajax({
//             url: '/getContent', // 서버에서 콘텐츠를 제공하는 엔드포인트
//             method: 'GET',
//             data: { page: page }, // 서버에 요청 데이터로 page 전달
//             success: function (response) {
//                 // 성공적으로 데이터를 받으면 #content-area에 삽입
//                 $('#content-area').html(response);
//
//                 // 탭 이동 시 애니메이션 적용
//                 setTimeout(function () {
//                     test(); // hori-selector 업데이트
//                 });
//             },
//             error: function () {
//                 // 에러 처리
//                 alert('콘텐츠를 로드하는 중 오류가 발생했습니다.');
//             }
//         });
//     });
// });