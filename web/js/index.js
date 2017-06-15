/*
 主界面的列表动态显示
 */

//noinspection JSJQueryEfficiency
$(".list-item").mouseover(function (e)
{
    $(e.delegateTarget).find(".check-img").addClass("hide");
    $(e.delegateTarget).addClass("grey");
    $(e.delegateTarget).addClass("lighten-3");
    $(e.delegateTarget).find(".check-checkbox").removeClass("hide");
    $(e.delegateTarget).find(".check-edit").removeClass("hide");
});

//noinspection JSJQueryEfficiency
$(".list-item").mouseleave(function (e)
{
    if (!$(e.delegateTarget).find("input").get(0).checked)
    {
        $(e.delegateTarget).find(".check-img").removeClass("hide");
        $(e.delegateTarget).find(".check-checkbox").addClass("hide");
    }
    $(e.delegateTarget).find(".check-edit").addClass("hide");
    $(e.delegateTarget).removeClass("grey");
    $(e.delegateTarget).removeClass("lighten-3");
});

var arr = [];
//noinspection JSJQueryEfficiency
$(".list-item").on("click", 'input', function (e)
{
    var i = $(e.delegateTarget).index() / 2;
    var name = $("#contactShowName" + i).text();
    if (e.target.checked)
    {
        arr.push(name);
        $("#delete-nav-btn").removeClass("hide");
        $('#reset-nav-wrapper').removeClass("blue");
    } else
    {
        var s = arr.indexOf(name);
        arr.splice(s, 1);
        if (arr.length === 0)
        {
            $("#delete-nav-btn").addClass("hide");
            $('#reset-nav-wrapper').addClass("blue");
        }
    }
    console.log(arr);
});

/*
 tag的列表动态显示
 */

$(".tag-list").click(function (e)
{
    if ($(e.delegateTarget).hasClass("active"))
    {
        $(e.delegateTarget).find(".up-pointer").addClass("hide");
        $(e.delegateTarget).find(".down-pointer").removeClass("hide");
    } else
    {
        $(e.delegateTarget).find(".up-pointer").removeClass("hide");
        $(e.delegateTarget).find(".down-pointer").addClass("hide");
    }
});

//noinspection JSJQueryEfficiency
$(".tag-item").mouseover(function (e)
{
    $(e.delegateTarget).find(".tag-delete").removeClass("hide");
});

//noinspection JSJQueryEfficiency
$(".tag-item").mouseleave(function (e)
{
    $(e.delegateTarget).find(".tag-delete").addClass("hide");
});

/*
 检测表单数据
 */
function checkForm(name, k)
{
    var id = "#" + name + ((k === -1) ? '' : k);
    var b = 0;
    $(id).find(":input").each(function ()
    {
        var vl = $(this).val();
        if (vl === null || vl === "")
        {
            b = 1;
        }
    });
    if (b === 1)
    {
        Materialize.toast('Please Fill Form!', 3000);
    } else
    {
        $(id).submit();
        $(id).modal('close');
    }
}

function delete_data(name, type)
{
    window.location.href = "DeleteServlet?deleteString=" + name + "&type=" + type;
}