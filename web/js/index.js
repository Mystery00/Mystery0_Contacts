$(".list-item").mouseover(function (e) {
    $(e.delegateTarget).find(".check-img").addClass("hide");
    $(e.delegateTarget).find(".check-checkbox").removeClass("hide");
    $(e.delegateTarget).find(".check-edit").removeClass("hide");
});

$(".list-item").mouseleave(function (e) {
    if (!$(e.delegateTarget).find("input").get(0).checked) {
        $(e.delegateTarget).find(".check-img").removeClass("hide");
        $(e.delegateTarget).find(".check-checkbox").addClass("hide");
    }
    $(e.delegateTarget).find(".check-edit").addClass("hide");
});