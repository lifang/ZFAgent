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
