<#--
FreeMarker模板文件主要由如下4个部分组成:
1、文本：直接输出的部分
2、注释
3、插值：即${...}或#{...}格式的部分,将使用数据模型中的部分替代输出
4、FTL指令：FreeMarker指定,和HTML标记类似,名字前加#予以区分,不会输出

例如判断 target 是否为null，如果不为 nll 则做xxx动作
<#if target??>
    xxxx
</#if>
 
freemarkder中字符串的比较方法和数字做到完全一样
<#if str == "success">
    xxx
</#if>


-->

<Body>
	<BaseServiceName>${serviceName}</BaseServiceName> 
	<#if name != 'wangliqiu'>
	<name>${name}</name>
	</#if>
			 
	<DataLists>
		<#list items as item>
			<ListRows>
			    <Row>${item_index}</Row>
  				<#list item as iv>  				
				<Item>
					<Row>${iv_index}</Row>
					<#if iv.value??>
					<DataValue>${iv.value}</DataValue>
					<#else>
					<DataValue>null</DataValue>
					</#if>
				</Item>
  				</#list>
  			</ListRows>
		</#list>
	</DataLists>

</Body>