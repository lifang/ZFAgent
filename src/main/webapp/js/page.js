/**
 * 初始化分页
 */
function initSystemPage(page) {
	page.rows = 2;// 每页条数
	page.indexPage = 1;// 起始页
	page.totalPage = 1;// 总页数
	page.total = 0;// 总条数
	page.pages = [];// 页码集合
	page.gotoPage = 1; // init gotoPage
}

/**
 * 计算分页
 * 
 * @param collectons
 */
function calcSystemPage(page, total) {
	
	if (total != null && total > 0) {
		page.total = total;
	}else{
		initSystemPage(page);
	}
	page.pages = [];// 页码集合
	page.totalPage = Math.ceil(page.total / page.rows); // 获取总页数
	if (page.indexPage >= 1 && page.indexPage < page.totalPage) { // 生成数字链接
		if (page.totalPage <= 10) {
			for (var i = 0; i < page.totalPage; i++) {
				page.pages[i] = (i + 1);
			}
		} else if (page.totalPage > 10 && page.indexPage <= 5) {
			for (var i = 0; i < 10; i++) {
				page.pages[i] = (i + 1);
			}
		} else if (page.totalPage > 10 && page.indexPage > 5) {
			if ((page.totalPage - page.indexPage) >= 5) {
				for (var i = 0; i < 10; i++) {
					page.pages[i] = page.indexPage - 5 + i + 1;
				}
			} else if ((page.totalPage - page.indexPage) < 5) {
				for (var i = 0; i < 10; i++) {
					page.pages[i] = page.totalPage - 10 + i + 1;
				}
			}
		}
	} else if (page.indexPage == 1 && page.totalPage > 1) {
		if (page.totalPage <= 10) {
			for (var i = 0; i < page.totalPage; i++) {
				page.pages[i] = page.indexPage + i;
			}
		} else if (page.totalPage > 10) {
			for (var i = 0; i < 10; i++) {
				page.pages[i] = page.totalPage - 10 + i;
			}
		}
	} else if (page.indexPage == page.totalPage && page.totalPage > 1) {
		if (page.totalPage <= 10) {
			for (var i = 0; i < page.totalPage; i++) {
				page.pages[i] = i + 1;
			}
		} else if (page.totalPage > 10) {
			for (var i = 0; i < 10; i++) {
				page.pages[i] = page.totalPage - 10 + i + 1;
			}
		}
	}
}
