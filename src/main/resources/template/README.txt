第一：E签宝电子关键字替换原则
          企业采用 姓名+"~" 进行设计替换。
          个人采用   身份证号+"~"进行设计替换
          
第二：参数规则：一份合同一份数据,合同的具体业务不在该系统进行
     参数包含两大部分。
     第一大部分：电子签章验证，包含企业和个人 .(要盖章那几个，全部提交过来)
     第二部分：包含合同的主体内容。（map和list）（要显示那些信息，就展示出来）
     
  apiParameters
        name
        idNumberOrEnterpriserCode
        userType
        esealpersons(list)
        esealEnterprises(list)
        contractContents(map)
        contractList(list)
  