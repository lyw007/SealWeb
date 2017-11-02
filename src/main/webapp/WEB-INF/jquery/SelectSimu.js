/*
author : zhupinglei_zjnb@163.com
desc : SelectSimu
data : 2012/07/19
dependon : jquery-1.7.js
*/
(function($){
	function _SelectSimu(here,options,index){
		var _this = this;
		this.$e = $(here),
		this.opts = options,
		this.index = index;
		this.init();
	}
	_SelectSimu.prototype = {
		init : function(){
			var _this = this;
			var className = (_this.$e.attr('id') ? '#'+_this.$e.attr('id')+' ' : 0) || (_this.$e.attr('class') ? '.'+_this.$e.attr('class')+' ' : 0);
			// var cssStr = 	'<style>'+
			// 				className + '{position:relative; width:'+_this.opts.width+'px; z-index:'+_this.opts.zIndex+';}'+
			// 				className + '.selectInput{position: relative; border:1px solid #ccc; padding:2px; background:#fff; width:100%; font-family: "宋体";}'+
			// 				className + '.selectInput span{width:80%; height: 13px; line-height:15px; padding:2px; display: block; overflow: hidden; border:none;}'+
			// 				className + '.selectInput i{display:block; position: absolute; right:1px; top:4px; background: url("'+_this.opts.imgSrc+'") center center no-repeat; width:14px; height: 14px;}'+
			// 				className + '.selectList{position: absolute; border:1px solid #ccc; width:100%; padding:2px; margin-top:-1px; cursor: default; font-family: "宋体"; background:#fff; display:none;}'+
			// 				className + '.selectList ul li{height: 20px; line-height: 20px; padding-left:2px; overflow:hidden;}'+
			// 				className + '.selectList ul li:hover{background: #0d7cd7; color:#fff;}'+
			// 				className + '.selectList ul li.hover{background: #0d7cd7; color:#fff;}'+
			// 				'</style>';
			// //css插入页面				
			// _this.$e.before(cssStr);
			// //html代码导入
			// var	selectStr = '<div class="selectInput">'+
			// 					'<input type="hidden" name="selectInput" value="'+(_this.opts.listValue[0] ? _this.opts.listValue[0] : "")+'" />'+
			// 					'<span>'+(_this.opts.listOption[0] ? _this.opts.listOption[0] : "")+'</span>'+
			// 					'<i></i>'+
			// 				'</div>'+
			// 				'<div class="selectList">'+
			// 					'<ul>';
			// for(var i = 0; i < _this.opts.listNum; i++){
			// 	selectStr += '<li value="'+(_this.opts.listValue[i] ? _this.opts.listValue[i] : "")+'">'+ (_this.opts.listOption[i] ? _this.opts.listOption[i] : "")+'</li>'
			// }
			// selectStr += '</ul></div>';
			// _this.$e.append(selectStr);
			_this.event();
		},
		event : function(){
			var _this = this;
			if( $.browser.msie && ($.browser.version == 6.0) ){
				_this.$e.find('.selectList li').hover(function(){
					$(this).addClass('hover').siblings().removeClass('hover');
				},function(){
					$(this).removeClass('hover');
				});
			}
			_this.$e.on('click','.selectInput',function(e){
				e.stopPropagation();
				_this.$e.find('.selectList').toggle();
			});
			$(document).click(function(){
				_this.$e.find('.selectList').hide();
			});
			_this.$e.find('.selectList').on('click','li',function(){
				var liValue = $(this).val(),
					liTxt = $(this).text();
				_this.$e.find('.selectInput input').val(liValue);
				_this.$e.find('.selectInput span').text(liTxt);
			});
		}
	}
	$.fn.SelectSimu = function(options){
		var opts = $.extend({},$.fn.SelectSimu.defaults,options);
		return this.each(function(index){
			this.SelectSimu = new _SelectSimu(this,opts,index);
		});
	}
	$.fn.SelectSimu.defaults = {
	// 	width : 150,
	// 	zIndex : 0,
	// 	listNum : 1,
	// 	listValue : ['0'],
	// 	listOption : ['请选择'],
	// 	// imgSrc : ''
	}
})(jQuery);
