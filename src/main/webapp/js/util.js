/**
 * 获取checkbox值
 */
function getCheckboxValue() {
	var str = "";
	$("input[name='checkboxs']:checkbox").each(function() {
		if ($(this).prop("checked")) {
			str += $(this).val() + ",";
		}
	});

	str = str.substring(0, str.length - 1);
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
