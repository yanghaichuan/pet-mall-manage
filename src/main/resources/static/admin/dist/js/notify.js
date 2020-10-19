$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/notify/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '状态', name: 'status', index: 'status', width: 60, formatter: statusFormatter},
            {label: '排序值', name: 'notifyRank', index: 'notifyRank', width: 100},
            {label: '内容', name: 'notifyText', index: 'notifyText', width: 200},
            {label: '添加时间', name: 'createTime', index: 'createTime', width: 120}
        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    function statusFormatter(cellvalue) {
        var v = "";
        if(cellvalue=="0"){
            v = "未发布";
        }else if(cellvalue=="1"){
            v = "已发布";
        }
        return v;
    }

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
    
});

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function notifyAdd() {
    reset();
    $('.modal-title').html('添加资讯');
    $('#notifyModal').modal('show');
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var notifyText = $("#notifyText").val();
    var notifyRank = $("#notifyRank").val();
    var status = $("input[type='radio']:checked").val();

    if (isNull(notifyText)) {
        swal("请输入内容", {
            icon: "error",
        });
        return;
    }

    var data = {
        "status": status,
        "notifyRank": notifyRank,
        "notifyText": notifyText
    };
    var url = '/admin/notify/save';
    var id = getSelectedRowWithoutAlert();
    if (id != null) {
        url = '/admin/notify/update';
        data = {
            "id": id,
            "status": status,
            "notifyRank": notifyRank,
            "notifyText": notifyText
        };
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                $('#notifyModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
            } else {
                $('#notifyModal').modal('hide');
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});

function notifyEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    //请求数据
    $.get("/admin/notify/info/" + id, function (r) {
        if (r.resultCode == 200 && r.data != null) {
            //填充数据至modal
            $("#notifyText").val(r.data.notifyText);
            $("#notifyRank").val(r.data.notifyRank);
            $("#id").val(r.data.id);
            var v = r.data.status;
            $("input[name='status'][value=v]").attr("checked",true);
        }
    });
    $('.modal-title').html('编辑资讯');
    $('#notifyModal').modal('show');
}

function deleteNotify() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/notify/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("删除成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    )
    ;
}


function reset() {
    $("#notifyText").val('');
    $("#notifyRank").val(0);
    $('#edit-error-msg').css("display", "none");
}