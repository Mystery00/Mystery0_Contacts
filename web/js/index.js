$(document).ready(function () {
    var ishide = false;
    $("#menu-toggle").on("click", function () {
        if (!ishide) {
            $(".asider-menu-default").addClass("menu-item-hide");
            $(".datalist-default").addClass("datalist-change");
            ishide = true;
        } else {
            $(".asider-menu-default").removeClass("menu-item-hide");
            $(".datalist-default").removeClass("datalist-change");
            ishide = false;
        }

    });

    //电话列表选择
    var arr = [];

    $(".list-item").on("click", 'input', function (e) {
        if (e.target.checked) {
            var i = $(e.delegateTarget).index();
            arr.push(i);
            arr.sort();
            $("#delete").show();
        } else {
            var i = $(e.delegateTarget).index();
            console.log(i);
            var s = arr.indexOf(i);
            console.log(s);
            arr.splice(s, 1);
            if (arr.length == 0) {
                $("#delete").hide();
            }
        }
        console.log(arr);
    });
    $(".list-item").mouseover(function (e) {
        $(e.delegateTarget).find(".check").addClass("Vvisible");
        $(e.delegateTarget).addClass("BKcolor");
    });
    $(".list-item").mouseleave(function (e) {
        if ($(e.delegateTarget).find("input").get(0).checked) {

        } else {
            $(e.delegateTarget).find(".check").removeClass("Vvisible");
            $(e.delegateTarget).removeClass("BKcolor");
        }
    })

    // 侧边栏点击后变色
    var preCheck = 0;

    $(".menu-list a").first().addClass("menu-checked");
    $(".menu-list a").each(function (index, node) {
        $(node).click(function () {
            $(".menu-list a[class~=menu-checked]").removeClass("menu-checked");
            $(this).addClass("menu-checked");
        });
    });
    // 事件代理解决动态添加
    $(".menu-list").on("click", "a", function (e) {


    });
});