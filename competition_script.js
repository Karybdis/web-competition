/*
    首页列表请求
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
    competitions: [],
    file_name: [], //存储后台出来的文件名字
    file_cnt: 0 //文件数量
  },
  mounted: function () {
    this.$nextTick(function () {
      message_table.get_msg("http://106.14.223.207:8081/competition/page/0");
    });
  },
  methods: {
    /*获取所有比赛信息*/
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
    /*删除比赛*/
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
    },

    /*打开下载界面*/
    show_window: function (event, id, name) {
      //获取后台的文件地址和文件名
      var div1 = document.getElementById("window_son");
      //			div1.style.display = "block";

      $.get("http://106.14.223.207:8081/competition/filename?id=" + id, function (files_name) {
        message_table.file_cnt = files_name.length;
        for (var i = 0; i < message_table.file_cnt; i++)
          Vue.set(message_table.file_name, i, files_name[i]);
        var btn = event.currentTarget;
        $(".div_title").text(name + "--下载");
        $("#window_son").css("transform", " translateY(0px)");
        $("#window_son").css("opacity", "1");
        message_table.creat_down_file(id);
      });

    },

    /*判断下载链接个数*/
    judge_href: function () {
      var leng = 0;
      leng = message_table.file_cnt;
      if (leng !== 0) {
        return leng;
      } else {
        alert('该生无相关材料,憋找了');
      }
    },

    /*添加某个文件下载*/
    creat_download_div: function (i,cid) {
      var MyDiv = document.getElementById("div_file");
      var div = document.createElement("div"); //createElement生成button对象
      div.className = "div_file_sheet";
      div.setAttribute("id", "div_" + i);
      MyDiv.appendChild(div);

      var bt = document.createElement("button"); //createElement生成button对象
      bt.className = "bt_file";
      bt.setAttribute("id", "bt_" + i);

      bt.innerHTML = message_table.file_name[i];
      bt.value="http://106.14.223.207:8081/competition/download?id="+cid+"&filename="+bt.innerHTML;
      bt.onclick = function () {
        window.open(bt.value);
      };
      div.appendChild(bt); //添加到页面
    },

    /*添加所有下载*/
    creat_down_file: function (cid) {
      var j = message_table.judge_href();

      for (var i = 0; i < j; i++) {
        message_table.creat_download_div(i,cid);
      }
    },

    /*从下载界面返回主页 */
    backhome: function () {
      var div1 = $("#window_son");
      div1.css("transform", " translateY(-464px)");
      div1.css("opacity", "0");
      //div1.style.left = "0px";
      //div1.style.top = "0px";

      $("#div_file").children("[id^=div]").remove(); 
    }
  }
});

/*====================================================================================================================================================================================================================================================================================================================*/
/*
    分页
*/
var pagination_link = new Vue({
  el: "#pglink",
  data: {
    page: 0,
    max_page: 1
  },
  mounted: function () {
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
      return this.page < 3 ? 1 : this.page - 1;
    },
    show_page2: function () {
      return this.page < 3 ? 2 : this.page;
    },
    show_page3: function () {
      return this.page < 3 ? 3 : this.page + 1;
    },
    show_page4: function () {
      return this.page < 3 ? 4 : this.page + 2;
    },
    show_page5: function () {
      return this.page < 3 ? 5 : this.page + 3;
    }
  }
});

/*====================================================================================================================================================================================================================================================================================================================*/
/*查询界面*/

/*点击查询*/
$("#btn_check").click(function () {
  $("div.mengban").css('display', 'block');
  $("button.find_btn_add").click();
});

/*查询组件*/
Vue.component('div_find', {
  props: ['value'],
  template: '<div class="div_find_big"><select class="find_text custom-input"><option value="获奖年度">获奖年度</option><option value="参赛级别">参赛级别</option><option value="获奖级别">获奖级别</option><option value="类别">类别</option><option value="作品名">作品名</option><option value="获奖学生姓名">获奖学生姓名</option><option value="指导老师">指导老师</option><option value="所属单位">所属单位</option></select><input class="find_text2 custom-input" type="text" :placeholder="value" /><button class="glyphicon sub-btn btn-primary find_btn_del find_btn_del_cnm" ></button></div>',
})


let num = 0;
let mengban = new Vue({
  el: "#mengban1",
  data: {
    div_find_if: [false, false, false, false, false, false, false, false],
    div_add_if: [true],
    sel_type: ["获奖年度", "参赛级别", "获奖级别", "类别", "作品名", "获奖学生姓名", "指导老师", "所属单位"],
    add_text: [],
    add_type: [],
    placeholder_text: {
      "获奖年度": "如2017，2016 ",
      "参赛级别": "国家，省，市，校，院",
      "获奖级别": "特等奖，一等奖，二等奖，三等奖",
      "类别": "新苗，大创，校立项，院立项，电子设计，智能车等",
      "作品名": "请输入作品名",
      "获奖学生姓名": "多个人查询请用逗号隔开",
      "指导老师": "多个人查询请用逗号隔开",
      "所属单位": "如自动化学院"
    },
    check_json: {
      year: "",
      gradeLarge: "",
      gradeSmall: "",
      nameLarge: "",
      nameDetail: "",
      student: "",
      teacher: "",
      belong: ""
    }
  },
  /*
          check_json：
          {
            year                    获奖年度
            gradeLarge              enum：国家级、省级
            gradeSmall              enum：一等奖、二等奖
            nameLarge               类别（省新苗等）
            nameDetail              名称
            student                 学生姓名
            teacher                 指导教师
            belong                  所属单位
          }
      		*/
  methods: {
    /*添加新的查询条目*/
    add: function () { //直接通过索引改变数组中的值，是不行的，，需要通过set()
      if (num >= 7) Vue.set(this.div_add_if, 0, false);
      if (num >= 8) {
        return;
      }

      Vue.set(this.div_find_if, num, true);
      this.read();
      //$("#div_find_"+num)[0].childNodes[0].value=sel_type.value;
      mengban.add_type[num] = $("#sel_type")[0].value;
      mengban.add_text[num] = $("#inp_text")[0].value;

      mengban.sel_type_chuli();
      inp_text.value = '';
      this.check();

      num++;
      // console.log(num);
    },
    /*读取查询条目*/
    read: function () {
      for (let i = 0; i < num && i <= 7 && num != 0; i++) {
        this.add_type[i] = $("#div_find_" + i)[0].childNodes[0].value;
        this.add_text[i] = $("#div_find_" + i)[0].childNodes[1].value;
      }
      mengban.sel_type_chuli();
    },
    /*辅助检查函数*/
    check: function () {
      for (let i = 0; i <= num; i++) {
        $("#div_find_" + i)[0].childNodes[0].value = mengban.add_type[i];
        $("#div_find_" + i)[0].childNodes[1].value = mengban.add_text[i];
      }
    },
    /*发出查询请求*/
    do_it: function () {
      $("div.mengban").css('display', 'none');
      mengban.read();
      for (let i = 0; i < num; i++) {
        this.check_json[mengban.zhuanhuan(mengban.add_type[i])] = mengban.add_text[i];
        // console.log(mengban.zhuanhuan(mengban.add_type[i]));
      }
      // loading_page.isActive = true;

      $.ajax({
        url: "http://106.14.223.207:8081/competition/query",
        data: JSON.stringify(mengban.check_json),
        async: false,
        type: "post",
        contentType: "application/json;charset=utf-8",
        success: function (json_data) {
          pagination_link.page = 0;
          pagination_link.max_page = 0;
          message_table.competitions.splice(0, message_table.competitions.length);
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
            // console.log(json_data);
          });
        }
      });
      // loading_page.isActive = false;
      mengban.chongzhi();
    },
    /*辅助函数*/
    zhuanhuan: function (ans) {
      if (ans == "获奖年度") return "year";
      if (ans == "类别") return "nameLarge";
      if (ans == "参赛级别") return "gradeLarge";
      if (ans == "获奖级别") return "gradeSmall";
      if (ans == "作品名") return "nameDetail";
      if (ans == "获奖学生姓名") return "student";
      if (ans == "指导老师") return "teacher";
      if (ans == "所属单位") return "belong";
    },
    sel_type_chuli: function () {
      $("#sel_type").val(function () {
        for (let i = 0, flag = 1; i < 8; i++) {
          flag = 1;
          for (let j = 0; j < mengban.add_type.length; j++) {
            if (mengban.sel_type[i] == mengban.add_type[j]) {
              flag = 0;
              break;
            }
          }
          if (flag == 1) {
            return mengban.sel_type[i];
          }
        }
      });
    },
    /*重置查询*/
    chongzhi: function () {
      num = 0;
      this.div_find_if = [false, false, false, false, false, false, false, false];
      this.div_add_if = [true];
      this.sel_type = ["获奖年度", "参赛级别", "获奖级别", "类别", "作品名", "获奖学生姓名", "指导老师", "所属单位"];
      this.add_text = [];
      this.add_type = [];
      this.check_json = {
        year: "",
        gradeLarge: "",
        gradeSmall: "",
        nameLarge: "",
        nameDetail: "",
        student: "",
        teacher: "",
        belong: ""
      }
    }
  }
})

/*删除查询条目*/
$("button.find_btn_del").click(function () {
  let div_del = $(this).parent();
  mengban.read();
  if (num == 8) {
    Vue.set(mengban.div_add_if, 0, true);
    $("#sel_type")[0].value = mengban.add_type[parseInt(div_del[0].id.charAt(div_del[0].id.length - 1), 10)];
  }
  num--;
  Vue.set(mengban.div_find_if, num, false);
  mengban.add_type.splice(parseInt(div_del[0].id.charAt(div_del[0].id.length - 1), 10), 1);
  mengban.add_text.splice(parseInt(div_del[0].id.charAt(div_del[0].id.length - 1), 10), 1);

  mengban.check();

});

/*退出查询*/
let flag_click = 1;
$("#mengban1").click(function () {
  flag_click = 0;
});

$("div.mengban").click(function () {
  if (flag_click == 1) {
    $("div.mengban").css('display', 'none');
    mengban.chongzhi();
  } else flag_click = 1;
})