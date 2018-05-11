/**
 * Created by chenlinfang on 2017/10/24.
 ＊国创移动应用能力平台js封装
 */

(function() {
	function connectWebViewJavascriptBridge(callback) {
		if(window.WebViewJavascriptBridge) {
			callback(WebViewJavascriptBridge)
		} else {
			document.addEventListener(
				'WebViewJavascriptBridgeReady',
				function() {
					callback(WebViewJavascriptBridge)
				},
				false
			);
		}
	}

	connectWebViewJavascriptBridge(function(bridge) {
		bridge.init(function(message, responseCallback) {
			var data = {
				'Javascript Responds': 'Wee!'
			};
			alert(message);
			responseCallback(data);
		});
	})

	var mplat = {

		getUserInfos: function(callback) {
			/**
			 * 获取用户信息方法
			 * @param {Object} options : param(参数) callback（回调函数，返回data ）
			 */
			window.WebViewJavascriptBridge.callHandler(
				'getUserInfos', {
					"params": "获取用户数据"
				},
				function(responseData) {
					if(callback != null)
						callback(responseData)
				}
			);
		},

		/**
		 * 拍照
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		takePhoto: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'takePhoto', {
					'params': str
				},
				function(responseData) {
					if(options.callback)
						options.callback(responseData);
				}
			);
		},

		/**
		 * 扫码
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		scanGetCode: function(callback) {
			window.WebViewJavascriptBridge.callHandler(
				'scanGetCode', {
					"params": "获取用户数据"
				},
				function(responseData) {
					if(callback != null)
						callback(responseData)
				}
			);
		},

		/**
		 * 从相册中选择
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		pickPic: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'pickPic', {
					'params': str
				},
				function(responseData) {
					if(options.callback)
						options.callback(responseData);
				}
			);

		},

		/**
		 * 显示加载框
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		showLoading: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'showLoading', {
					"params": str
				}
			);
		},

		/**
		 * 取消加载框
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		cancelLoading: function(options) {
			window.WebViewJavascriptBridge.callHandler('cancelLoading');
		},

		/**
		 * 定位
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		getLocationInfo: function(options) {
			window.WebViewJavascriptBridge.callHandler('getLocationInfo');
		},

		/**
		 * 打电话
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		telephoneCall: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'telephoneCall', {
					"params": str
				}
			);
		},

		/**
		 * 发送短信
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		shortMessage: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'shortMessage', {
					"params": str
				});
		},



		/**
		 * 获取手机硬件信息
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		getPhoneDeviceName: function(options) {
			window.WebViewJavascriptBridge.callHandler(
				'getPhoneDeviceName',
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},

		/**
		 * 数据库－建表操作
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		createTableInfo: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'createTableInfo', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},

		/**
		 * 数据库－插入数据操作
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		insertInfo: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'insertInfo', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},

		/**
		 * 数据库查询数据操作
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		selectInfos: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'selectInfos', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},

		/**
		 * 数据库－删除数据操作
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		deleteInfo: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'deleteInfo', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},

		/**
		 * 数据库－更新数据操作
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		updateInfo: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'updateInfo', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},

		/**
		 * 数据库－删除表操作
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		dropTable: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'dropTable', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},
		/**
		 * 锁屏
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		lockScreen: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'lockScreen', {
					"params": str
				}
			);
		},

		/**
		 * 截屏
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		screenShot: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'screenShot', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},

		/**
		 * 调节屏幕亮度
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		changeScreenIntensity: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'changeScreenIntensity', {
					"params": str
				});
		},

		/**
		 * 露营
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		recordVoice: function(options) {
			var params = options.params;
			//录音最短时间
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'recordVoice', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},

		/**
		 * 录音播放
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		playVoice: function(options) {
			var params = options.params;
			//录音最短时间
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'playVoice', {
					"params": str
				}
			);
		},

		//上传
		uploadVoice: function(options) {
			var params = options.params;
			//录音最短时间
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'uploadVoice', {
					"params": str
				}
			);
		},
		//录制视频
		recordVideo: function(options) {
			var params = options.params;
			//录音最短时间
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'recordVideo', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},
		// 播放视频
		playVideo: function(options) {
			var params = options.params;
			//录音最短时间
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'playVideo', {
					"params": str
				});
		},
		//获取包名
		getPackInfor: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'getPackInfor', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},
		//获取wifi信息
		getWifiInfor: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'getWifiInfor', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},
		//分享
		shareTest: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'shareTest', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},

		/**
		 * 获取电子签名方法
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		getElecSignPic: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'getElecSignPic', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},

		/**
		 * 打水印
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		makeMark: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				params.photoPath = params.photoPath.replace(/[\/]/g, '$');
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'makeMark', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},

		/**
		 * 上传图片
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		uploadPicByPath: function(options) {
			var params = options.params;
			var str = "";
			if(params != null) {
				params.photoPath = params.photoPath.replace(/[\/]/g, '$');
				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
			}
			window.WebViewJavascriptBridge.callHandler(
				'uploadPicByPath', {
					"params": str
				},
				function(responseData) {
					if(options.callback != null)
						options.callback(responseData)
				}
			);
		},
/**
		 * 判断横竖屏
		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
		 */
		LandscapeOrPortrait: function(callback) {
			window.WebViewJavascriptBridge.callHandler(
				'LandscapeOrPortrait', {},
				function(responseData) {
					if(callback != null)
						callback(responseData);
				}
			);
		},

		/**
        		 * 通讯录
        		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
        		 */
        		addressBook: function(callback) {
        		window.WebViewJavascriptBridge.callHandler(
                				'addressBook', {},
                				function(responseData) {
                					if(callback != null)
                						callback(responseData);
                				}
                			);
        		},
        		/**
                		 * 文件下载
                		 * @param {Object} options : param(参数) callback（回调函数，返回data ）
                		 */
                		DownLoadFile: function(options) {
                			var params = options.params;
                			var str = "";
                			if(params != null) {
                				params.loadpath = params.loadpath.replace(/[\/]/g, '$');
                				str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
                			}
                			window.WebViewJavascriptBridge.callHandler(
                				'DownLoadFile', {
                					"params": str
                				},
                				function(responseData) {
                					if(options.callback != null)
                						options.callback(responseData)
                				}
                			);
                		},/**
                           * 定位
                           * @param {Object} options : param(参数) callback（回调函数，返回data ）
                           */
                          getLocationInfo: function(callback) {
                           window.WebViewJavascriptBridge.callHandler(
                           'getLocationInfo', {},
                           function(responseData) {
                           if(callback != null)
                           callback(responseData);
                            }
                            );
                            },
                         /**
                         * 获取文件大小
                          * @param {Object} options : param(参数) callback（回调函数，返回data ）
                          */
                         GetFileSize: function(options) {
                          var params = options.params;
                          var str = "";
                          if(params != null) {
                           params.savepath = params.savepath.replace(/[\/]/g, '$');
                           str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
                           }
                            window.WebViewJavascriptBridge.callHandler(
                            'GetFileSize', {
                            "params": str
                            },
                            function(responseData) {
                            if(options.callback != null)
                             options.callback(responseData)
                             }
                             );
                             },/**
                               * 删除文件
                                * @param {Object} options : param(参数) callback（回调函数，返回data ）
                                */
                                 DeleteFile: function(options) {
                                 var params = options.params;
                                 var str = "";
                                 if(params != null) {
                                 params.filepath = params.filepath.replace(/[\/]/g, '$');
                                 str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
                                 }
                                 window.WebViewJavascriptBridge.callHandler(
                                 'DeleteFile', {
                                 "params": str
                                 },
                                 function(responseData) {
                                 if(options.callback != null)
                                 options.callback(responseData)
                                 }
                                 );
                                 },
                                 /**
                                 * 中断文件下载
                                 * @param {Object} options : param(参数) callback（回调函数，返回data ）
                                 */
                                 CancelDownLoad: function(callback) {
                                 window.WebViewJavascriptBridge.callHandler(
                                 'CancelDownLoad', {},
                                 function(responseData) {
                                 if(callback != null)
                                 callback(responseData);
                                 }
                                 );
                                 },
                               /**
                                * 获取getIntent
                                * @param {Object} options : param(参数) callback（回调函数，返回data ）
                                 */
                                 getIntent: function(options) {
                                 var params = options.params;
                                 var str = "";
                                 if(params != null) {
                                 	str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
                                 }
                                 window.WebViewJavascriptBridge.callHandler(
                                 'getIntent', {
                                  "params": str
                                 },
                                 function(responseData) {
                                if(options.callback != null)
                                	options.callback(responseData)
                                 }
                                 );
                                  },

/**
                                * 显示加载框
                                * @param {Object} options : param(参数) callback（回调函数，返回data ）
                                 */
                                 showLoading: function(options) {
                                 var params = options.params;
                                 var str = "";
                                 if(params != null) {
                                 	str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
                                 }
                                 window.WebViewJavascriptBridge.callHandler(
                                 'showLoading', {
                                  "params": str
                                 },
                                 function(responseData) {
                                if(options.callback != null)
                                	options.callback(responseData)
                                 }
                                 );
                                  },
  /**
                                 * 关闭加载框
                                 * @param {Object} options : param(参数) callback（回调函数，返回data ）
                                 */
                                 cancelLoading: function(callback) {
                                 window.WebViewJavascriptBridge.callHandler(
                                 'cancelLoading', {},
                                 function(responseData) {
                                 if(callback != null)
                                 callback(responseData);
                                 }
                                 );
                                 },
 /**
                                 * 弹出数字软键盘
                                 * @param {Object} options : param(参数) callback（回调函数，返回data ）
                                 */
                                 showkeyboard: function(options) {
                                  var params = options.params;
                                  var str = "";
                                  if(params != null) {
                                  str = typeof(params) == "object" ? JSON.stringify(params) : params + str;
                                  }
                                 window.WebViewJavascriptBridge.callHandler(
                                 'showkeyboard', {
                                     "params": str
                                     },
                                 function(responseData) {
                                 if(options.callback != null)
                                 options.callback(responseData);
                                 }
                                 );
                                 },
	}
	window.Mplat = mplat;
})()