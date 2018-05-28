// id                      序号
// year                    获奖年度
// grade                   获奖级别
// name                    竞赛名称
// total_students          学生姓名
// teacher1                指导教师1
// teacher2                指导教师2
// belong                  所属单位
// approval_status         审核状态
// certificate             证书地址
index = 1;

window.onload = function () {

  document.getElementById("first").style.display = "block";
  document.getElementById("sec").style.display = "none";
};

new Vue({
  el: "#first-link",
  methods: {
    show_first: function () {
      document.getElementById("first").style.display = "block";
      document.getElementById("sec").style.display = "none";
      document.getElementById("page-title").innerText = "竞赛档案列表";
    }
  }
});

new Vue({
  el: "#sec-link",
  methods: {
    show_sec: function () {
      document.getElementById("sec").style.display = "block";
      document.getElementById("first").style.display = "none";
      document.getElementById("page-title").innerText = "添加档案";
    }
  }
});

var message_table = new Vue({
  el: "#total-message",
  data: {
    competitions: []
  },
  mounted() {
    this.$nextTick(function () {
      $.get("http://106.14.223.207:8081/competition/list", function (json_data) {
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
});

var ceritificate_pic = new Vue({
  el: "#post-certificate",
  data: {
    file: ""
  },
  methods: {
    post_pic: function () {
      var image = new FormData();
      image.append('file', ceritificate_pic.file);
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
      var reader = new FileReader()
      var that = this
      reader.readAsDataURL(file)
      reader.onload = function (e) {
        that.pic = this.result;
        ceritificate_pic.file = file;
        document.getElementById("preview").src = e.target.result;
      }
    }
  }
});