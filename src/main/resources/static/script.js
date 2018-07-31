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
    加载界面
*/
var loading_page = new Vue({
  el: ".loading-page",
  data: {
    isActive: true
  }
});



var message_table = new Vue({
  el: "#total-message",
  data: {
    competitions: [],
    isActive: true
  },
  mounted() {
    this.$nextTick(function () {
      /*
          json_data格式：
          {
            id                      序号
            year                    获奖年度
            gradeLarge              enum：国家级、省级
            gradeSmall              enum：一等奖、二等奖
            nameLarge               类别（省新苗等）
            nameDetail              名称
            student                 学生姓名
            teacher                 指导教师
            belong                  所属单位
            status                  审核状态
            certificate             证书地址
          }
      */
      $.get("http://106.14.223.207:8081/competition/list", function (json_data) {
        $.each(json_data, function (idx, item) {
          message_table.competitions.push({
            id: item.id,
            year: item.year,
            award: item.gradeLarge + item.gradeSmall,
            category: item.nameLarge,
            name: item.nameDetail,
            students: item.student,
            teachers: item.teacher,
            belong: item.belong,
            approval_status: item.status,
            certificate: item.certificate
          });
          loading_page.isActive = false;
        });
      });
    });
  },
  methods: {
    delete_c: function (cid) {
      var tmp_c = {
        id: cid
      };
      console.log(cid);
      $.ajax({
        url: "http://106.14.223.207:8081/competition/",
        data: JSON.stringify(tmp_c),
        type: "delete",
        contentType: "application/json;charset=utf-8",
        success: function (json_data) {
          location.reload();
        }
      });
    }
  }
});

/*
      查询表单
*/
var query_table = new Vue({
  el: "#query-message",
  data: {
    query_json: {
      year: "",
      grade: "",
      name: "",
      student: "",
      teacher: ""
    }
  },
  methods: {
    query: function () {
      console.log(query_table.query_json);
      $.ajax({
        url: "http://106.14.223.207:8081/competition/query",
        data: JSON.stringify(query_table.query_json),
        type: "post",
        contentType: "application/json;charset=utf-8",
        success: function (json_data) {
          $.each(message_table.competitions, function (idx, item) {
            message_table.competitions.pop();
          });
          $.each(json_data, function (idx, item) {
            message_table.competitions.push({
              id: item.id,
              year: item.year,
              grade: item.grade,
              name: item.name,
              total_students: item.student1 +
                (item.student2 == "" ? "" : "、" + item.student2) +
                (item.student3 == "" ? "" : "、" + item.student3),
              teacher1: item.teacher1,
              teacher2: item.teacher2,
              belong: item.belong,
              approval_status: item.status,
              certificate: item.certificate
            });
          });
        }
      });
    }
  }
});

/*
    添加信息
*/
var add_table = new Vue({
  el: "#add-message",
  data: {
    add_json: {
      year: "",
      grade: "",
      name: "",
      student1: "",
      student2: "",
      student3: "",
      teacher1: "",
      teacher2: "",
      belong: "",
      status: "",
      certificate: ""
    }
  },
  methods: {
    add: function () {
      var tip_msg = "";
      if (add_table.add_json.year == "")
        tip_msg += (tip_msg == "" ? "" : "、") + "获奖年度";
      if (add_table.add_json.grade == "")
        tip_msg += (tip_msg == "" ? "" : "、") + "获奖级别";
      if (add_table.add_json.name == "")
        tip_msg += (tip_msg == "" ? "" : "、") + "所获奖项";
      if (add_table.add_json.student1 == "")
        tip_msg += (tip_msg == "" ? "" : "、") + "学生姓名（至少一个）";
      if (add_table.add_json.teacher1 == "")
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
            location.reload();
          }
        });
      }
    }
  }
});

/*
    图片预览(修改或删除)
 */
var ceritificate_pic = new Vue({
  el: "#post-certificate",
  data: {
    file: ""
  },
  methods: {
    post_pic: function () {
      var image = new FormData();
      image.append("file", ceritificate_pic.file);
      console.log(image.file);
      $.ajax({
        url: "http://106.14.223.207:8081/competition/upload",
        data: image,
        type: "post",
        contentType: false,
        processData: false,
        success: function (redata) {
          add_table.add_json.certificate = redata;
          document.getElementById("preview").src = redata;
          document.getElementById("oktoload").style.display = "inline-flex";
        }
      });
    },
    changeImage: function (e) {
      var file = e.target.files[0];
      var reader = new FileReader();
      var that = this;
      reader.readAsDataURL(file);
      reader.onload = function (e) {
        that.pic = this.result;
        ceritificate_pic.file = file;
        document.getElementById("preview").src = e.target.result;
      };
    }
  }
});

window.onload() = function () {
  loading_page.isActive = true;
}