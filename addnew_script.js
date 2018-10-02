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
    },
    tr_show_name:[false,false,false,false,false]
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
    },
    add_stu:function(){
    	for(let i=0;i<4;i++){
    		if(this.tr_show_name[i]==false)	{
    			Vue.set(this.tr_show_name,i,true);
    			break;
    		}
    	}
    },
    add_tea:function(){
    	Vue.set(this.tr_show_name,4,true);
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
