/**
 * 获取checkbox值
 */
function getCheckboxValue() {
	var arrayObj = new Array();// 创建一个数组
	$("input[name='checkboxs']:checkbox").each(function() {
		if ($(this).prop("checked")) {
			// str += $(this).val() + ",";
			arrayObj.push($(this).val());
		}
	});

	return arrayObj;
	// alert(str);

}

/**
 * 选中checkbox
 * 
 * @param i
 */
function isCheckBoxChecked(i) {
	$("input[name='checkboxs']:checkbox").each(function() {
		// alert($(this).val());
		if (i == $(this).val()) {
			$(this).prop("checked", true);
			return;
		}
	});
}

/**
 * 计算字符串长度(英文占1个字符，中文汉字占2个字符)
 * 
 * @param str
 * @returns {Number}
 */
function strlen(str) {
	var len = 0;
	for (var i = 0; i < str.length; i++) {
		var c = str.charCodeAt(i);
		// 单字节加1
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
			len++;
		} else {
			len += 2;
		}
	}
	// alert(len);
	return len;
}
