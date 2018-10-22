/*
    首页列表请求
    首页列表条目与json_data的映射关系
    {
        year                                      获奖年度
        category = name_large                     类别（省新苗等）
        award = grade_large + grade_small         奖级
        name = name_detail                        名称
        students = student                        学生姓名
        teachers = teacher                        指导教师
        belong                                    所属单位
        approval_status = status                  审核状态
        certificate                               证书地址（待定）
    }
*/

/*
    添加信息
*/
var add_table = new Vue({
  el: "#add-message",
  data: {
    add_json: {
      year: "",
      nameLarge: "",
      gradeLarge: "",
      gradeSmall: "",
      nameDetail: "",
      student: "",
      teacher: [],
      belong: "",
      status: "",
      certificate: ""
    },
    student1: "",
    student2: "",
    student3: "",
    student4: "",
    student5: "",
    teacher1: "",
    teacher2: "",
    tr_show_name: [false, false, false, false, false]
  },
  methods: {
    add: function () {
      if (add_table.student1 != "")
        add_table.add_json.student += (add_table.add_json.student == "" ? "" : "、") + add_table.student1;
      if (add_table.student2 != "")
        add_table.add_json.student += (add_table.add_json.student == "" ? "" : "、") + add_table.student2;
      if (add_table.student3 != "")
        add_table.add_json.student += (add_table.add_json.student == "" ? "" : "、") + add_table.student3;
      if (add_table.student4 != "")
        add_table.add_json.student += (add_table.add_json.student == "" ? "" : "、") + add_table.student4;
      if (add_table.student5 != "")
        add_table.add_json.student += (add_table.add_json.student == "" ? "" : "、") + add_table.student5;
      if (add_table.teacher1 != "")
        add_table.add_json.teacher += (add_table.add_json.teacher == "" ? "" : "、") + add_table.teacher1;
      if (add_table.teacher2 != "")
        add_table.add_json.teacher += (add_table.add_json.teacher == "" ? "" : "、") + add_table.teacher2;
      var tip_msg = "";
      if (add_table.add_json.year == "")
        tip_msg += (tip_msg == "" ? "" : "、") + "获奖年度";
      if (add_table.add_json.nameLarge == "")
        tip_msg += (tip_msg == "" ? "" : "、") + "类别";
      if (add_table.add_json.gradeLarge == "" || add_table.add_json.gradeSmall == "")
        tip_msg += (tip_msg == "" ? "" : "、") + "获奖级别";
      if (add_table.add_json.nameDetail == "")
        tip_msg += (tip_msg == "" ? "" : "、") + "获奖名称";
      if (add_table.add_json.student == "")
        tip_msg += (tip_msg == "" ? "" : "、") + "学生姓名（至少一个）";
      if (add_table.add_json.teacher == "")
        tip_msg += (tip_msg == "" ? "" : "、") + "教师姓名（至少一个）";
      if (add_table.add_json.belong == "")
        tip_msg += (tip_msg == "" ? "" : "、") + "所属单位";
      if (tip_msg != "") alert("请填写" + tip_msg + "!");
      else {
        $.ajax({
          url: "http://106.14.223.207:8081/competition/",
          data: JSON.stringify(add_table.add_json),
          type: "post",
          contentType: "application/json;charset=utf-8",
          success: function (json_data) {
            // location.reload();
            $('body > div > div > div.demo-drawer.mdl-layout__drawer.mdl-color--blue-grey-900.mdl-color-text--blue-grey-50 > nav > a:nth-child(1) > i').trigger('click');
          }
        });
      }
    },
    add_stu: function () {
      for (let i = 0; i < 4; i++) {
        if (this.tr_show_name[i] == false) {
          Vue.set(this.tr_show_name, i, true);
          break;
        }
      }
    },
    add_tea: function () {
      Vue.set(this.tr_show_name, 4, true);
    }
  }
});

/*
    图片预览(修改或删除)
 */
var ceritificate_pic = new Vue({
  el: "#post-certificate",
  data: {
    file: "",
    upload_percentage: ""
  },
  methods: {
    post_file: function () {
      var data = new FormData();
      for(var i=0;i<$('#postFile > input[type="file"]')[0].files.length;i++)
        data.append("file",$('#postFile > input[type="file"]')[0].files[i]);
      $.ajax({
        url: "http://106.14.223.207:8081/competition/upload",
        data: data,
        type: "post",
        contentType: false,
        processData: false,
        xhr: function xhr() {
          let xhr = $.ajaxSettings.xhr();
          if (xhr.upload) {
            xhr.upload.addEventListener('progress', function (e) {
              ceritificate_pic.upload_percentage = (e.loaded / e.total).toFixed(2);
            }, false);
          }
          return xhr;
        },
        success: function (redata) {
          add_table.add_json.certificate = redata;
        }
      });
    },
    post_excel: function () {
      var data = new FormData();
      data.append("file",$('#post-certificate > div.upload-from-excel > input[type="file"]')[0].files[0]);
      $.ajax({
        url: "http://106.14.223.207:8081/competition/excel",
        data: data,
        type: "post",
        contentType: false,
        processData: false,
        success: function (redata) {
          add_table.add_json.certificate = redata;
        }
      });
    }
  }
});