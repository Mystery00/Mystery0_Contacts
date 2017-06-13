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

function checkForm(thisForm,i) {
    with (thisForm)
    {
        if (thisForm.input.value===null||thisForm.input.value==="")
        {
            console.log('test');
            console.log('test'+i);
            alert('test'+i);
        }
        // document.getElementById('new-form'+i).submit();
        return false;
    }
}