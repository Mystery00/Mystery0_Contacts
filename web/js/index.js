$(".list-item").mouseover(function (e) {
    $(e.delegateTarget).find(".check-img").addClass("hide");
    $(e.delegateTarget).addClass("grey");
    $(e.delegateTarget).addClass("lighten-3");
    $(e.delegateTarget).find(".check-checkbox").removeClass("hide");
    $(e.delegateTarget).find(".check-edit").removeClass("hide");
});

$(".list-item").mouseleave(function (e) {
    if (!$(e.delegateTarget).find("input").get(0).checked) {
        $(e.delegateTarget).find(".check-img").removeClass("hide");
        $(e.delegateTarget).find(".check-checkbox").addClass("hide");
    }
    $(e.delegateTarget).find(".check-edit").addClass("hide");
    $(e.delegateTarget).removeClass("grey");
    $(e.delegateTarget).removeClass("lighten-3");
});

$(".tag-list").click(function (e) {
    if ($(e.delegateTarget).hasClass("active"))
    {
        $(e.delegateTarget).find(".up-pointer").addClass("hide");
        $(e.delegateTarget).find(".down-pointer").removeClass("hide");
    }else
    {
        $(e.delegateTarget).find(".up-pointer").removeClass("hide");
        $(e.delegateTarget).find(".down-pointer").addClass("hide");
    }
});

function checkForm(k) {
    var id = "#new-form" + ((k === -1) ? '' : ('-' + k));
    var b = 0;
    $(id).find(":input").each(function () {
        var vl = $(this).val();
        if (vl === null || vl === "") {
            b = 1;
        }
    });
    if (b === 1) {
        alert("Please Fill Form!");
    } else {
        $(id).submit();
        $(id).modal('close');
    }
}