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


/*
    信息列表
*/
var message_table = new Vue({
  el: "#total-message",
  data: {
    competitions: []
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
      $.get("http://106.14.223.207:8081/competition/page/0", function (json_data) {
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
          // console.log(json_data);
        });
      });
    });
  },
  methods: {
    get_msg: function (url) {
      loading_page.isActive = true;
      message_table.competitions.splice(0, message_table.competitions.length);
      $.get(url, function (json_data) {
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
          // console.log(json_data);
        });
      });
    },
    delete_c: function (cid) {
      $.ajax({
        url: "http://106.14.223.207:8081/competition/",
        data: JSON.stringify({
          id: cid
        }),
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
    分页
*/
var pagination_link = new Vue({
  el: "#pglink",
  data: {
    page: 0,
    max_page: 1
  },
  mounted() {
    this.$nextTick(function () {
      $.get('http://106.14.223.207:8081/competition/getpage', function (pg) {
        pagination_link.max_page = pg - 1;
      })
    })
  },
  methods: {
    getpage: function (pg) {
      message_table.get_msg("http://106.14.223.207:8081/competition/page/" + pg);
    }
  },
  computed: {
    show_page1: function () {
      return this.page<3?1:this.page-1;
    },
    show_page2: function () {
      return this.page<3?2:this.page;
    },
    show_page3: function () {
      return this.page<3?3:this.page+1;
    },
    show_page4: function () {
      return this.page<3?4:this.page+2;
    },
    show_page5: function () {
      return this.page<3?5:this.page+3;
    }
  }
});


// query

// $.ajax({
//   url: "http://106.14.223.207:8081/competition/query",
//   data: JSON.stringify(query_table.query_json),
//   type: "post",
//   contentType: "application/json;charset=utf-8",
//   success: function (json_data) {
//     $.each(message_table.competitions, function (idx, item) {
//       message_table.competitions.pop();
//     });
//     $.each(json_data, function (idx, item) {
//       message_table.competitions.push({
//         id: item.id,
//         year: item.year,
//         grade: item.grade,
//         name: item.name,
//         total_students: item.student1 +
//           (item.student2 == "" ? "" : "、" + item.student2) +
//           (item.student3 == "" ? "" : "、" + item.student3),
//         teacher1: item.teacher1,
//         teacher2: item.teacher2,
//         belong: item.belong,
//         approval_status: item.status,
//         certificate: item.certificate
//       });
//     });
//   }
// });