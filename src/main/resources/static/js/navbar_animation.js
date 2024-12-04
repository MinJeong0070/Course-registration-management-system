function test() {
    // 네비게이션 바 요소 전체 선택
    var tabsNewAnim = $("#navbarSupportedContent");

    // 현재 활성화된(active) 항목 선택
    var activeItemNewAnim = tabsNewAnim.find(".active");

    // 활성화된 항목의 높이와 너비 구하기
    var activeWidthNewAnimHeight = activeItemNewAnim.innerHeight();
    var activeWidthNewAnimWidth = activeItemNewAnim.innerWidth();

    // 활성화된 항목의 위치 정보 가져오기 (top, left 값 포함)
    var itemPosNewAnimTop = activeItemNewAnim.position();
    var itemPosNewAnimLeft = activeItemNewAnim.position();

    // 강조 표시 위치 설정
    $(".hori-selector").css({
        top: itemPosNewAnimTop.top + "px",
        left: itemPosNewAnimLeft.left + "px",
        height: activeWidthNewAnimHeight + "px",
        width: activeWidthNewAnimWidth + "px"
    });
}

// 페이지 로드 후 실행되도록 설정
$(document).ready(function () {
    // 페이지 로드 후 test 함수 호출하여 활성화된 항목의 기본 위치 설정하기
    var activePage = localStorage.getItem("activePage");

    // 이전에 클릭된 항목이 있는 경우 해당 항목에 활성화(active) 클래스 추가하기
    if (activePage) {
        $("#navbarSupportedContent ul li").removeClass("active");
        $(`#navbarSupportedContent ul li a[data-page="${activePage}"]`).parent().addClass("active");
    }

    // 강조 표시 설정
    test();
});

// 윈도우 리사이즈될 때 강조 표시 위치 재설정하기
$(window).on("resize", function () {
    setTimeout(function () {
        test(); // 윈도우 크기 변경 시 강조 표시 위치 및 크기 재설정
    }, 500);
});

// 네비게이션 바의 li 항목 클릭 시 강조 표시 이동 설정하기
$("#navbarSupportedContent").on("click", "li", function () {
    // 모든 li 항목에서 active 클래스 제거하기
    $("#navbarSupportedContent ul li").removeClass("active");

    // 클릭된 항목에 active 클래스 추가하기
    $(this).addClass("active");

    // 클릭된 페이지 정보 저장 (localStorage에 저장하여 새로고침 시 유지)
    var page = $(this).find("a").data("page");
    localStorage.setItem("activePage", page);

    // 강조 표시 이동 설정하기
    test();
});