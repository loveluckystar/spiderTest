/**
 * Version    Date      By        Description
 * ======================================================================================
 *            20090608  hrchen    添加方法getFilePath(String)和uploadNotAdd(Row, HttpServletRequest, String)
 */
package com.spiderTest.web;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.netease.product.Constant;
import com.netease.product.ParamProxy;
import com.netease.product.datasource.SpObserver;
import com.netease.product.service.Img2Service;
import com.netease.product.util.DateUtil;
import com.netease.product.util.FileUtil;
import com.netease.product.util.Imagick;
import com.netease.product.util.Row;
import com.netease.product.util.StringUtil;

/**
 * @author Administrator
 * 
 */
public abstract class BaseController{

	protected static final Logger logger = Logger.getLogger(BaseController.class);

  protected static final int BUFFER_SIZE = 10 * 1024;

  protected static final String[] IMAGETYPE = { "image/jpeg", "image/jpg",
      "image/pjpeg", "image/gif", "image/png", "image/x-png" };


  protected void setHeader(HttpServletResponse response) {
    response.setContentType("text/html;charset=GBK");

  }
  
  protected void noCache(HttpServletResponse resp){
    resp.setHeader("Pragma", "No-cache");
    resp.setHeader("Cache-Control", "no-cache");
    resp.setDateHeader("Expires", 0);
  }

  protected void renderText(String text, HttpServletResponse response ) {
    // 这里不加的话，AJAX会缓存每次取的数据，从而造成数据不能更新
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    setHeader(response);
    try {
      response.getWriter().write(text);
    } catch (Exception e) {
      logger.error(e);
    }
  }

  /**
   * 获得前一页面
   * 
   * @return
   */
  protected String getReferer(HttpServletRequest request) {
    return request.getHeader("REFERER");
  }

  /** 页面重定向 */
  protected String sendRedirect(String url, HttpServletResponse response) {
    try {
    	response.sendRedirect(url);
    } catch (Exception e) {
      logger.error(e);
    }
    return null;
  }
  
  /**
   * 跳转到404页面
   * @param response
   * @return
   */
  protected String sendTo404(HttpServletResponse response){
    String url = "http://" + SpObserver.getCity() + ".house.163.com/special/0087jt/error_house.html";
    return sendRedirect(url,response);
  }

  /**
   * 弹窗
   * 
   * @param response
   * @param content
   * @param url
   * @throws Exception
   */
  public void renderScriptText(String content, String url, HttpServletResponse response) {
    String text = "<script>alert('" + content + "');window.location.href='"
        + url + "';</script>";
    renderText(text, response);
  }

  /**
   * 弹窗
   * 
   * @param response
   * @param content
   * @param url
   * @throws Exception
   */
  public void renderParentJs(String content, String url, HttpServletResponse response) {
    String text = "<script>alert('" + content
        + "');parent.window.location.href='" + url + "';</script>";
    renderText(text, response);
  }

  /**
   * 输出JS代码
   * 
   * @param response
   * @param content
   * @param url
   * @throws Exception
   */
  public void renderScript(String jscode, HttpServletResponse response) {
    String text = "<script>" + jscode + "</script>";
    renderText(text, response);
  }

  /**
   * 子窗口返回数据，把值传给父窗口后，关闭子窗口
   * 
   * @param response
   * @param content
   * @param url
   * @throws Exception
   */
  public void renderScriptText(String content, HttpServletResponse response) {
    renderText("<script>document.write('<div style=\"font-size:12px;text-align:center;color:red\">"
        + content
        + "<br><input type=\"button\" value=\"确定并关闭窗口\" onclick=\"parent.location.href=parent.location.href;parent.Dialog.close();\"></div>');</script>", response);
  }

  /**
   * 弹出提示框后返回上一页面并刷新（保证了参数与第一次进入上一页面相同）
   * 
   * @param content
   */
  public void alert(String content, HttpServletResponse response) {
    String text = "<script>alert('" + content
        + "');window.history.back();location.reload();</script>";
    renderText(text, response);
  }

  /**
   * 获取登录的用户名
   * 
   * @return
   */
  public String getLoginUserName(HttpServletRequest request) {
    Row adminRow = Constant.FACADE.chklogin(request);
    return adminRow.gets("username", "");
  }

  /**
   * 获取登陆用户的频道
   * 
   * @return
   */
  public String getUserChannel(HttpServletRequest request) {
    return Constant.FACADE.chklogin(request).gets("channel", "");
  }
  /**
   * 获取图片上传到服务器的相对路径
   * @param parent父目录
   * @return
   */
  protected String getFilePath(String parent) {
    StringBuffer path = new StringBuffer("/photo/");
    path.append(Constant.getCmsChannelId()).append("/");
    if(!StringUtil.isEmpty(parent)){
      path.append(parent).append("/");
    }
    path.append(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
    File file = new File("/home/htmlfile" + path.toString());
    if (!file.exists()) {
      file.mkdirs();
    }
    return path.toString();
  }
  
  protected String getFilePath(){
    return getFilePath(null);
  }

  /**
   * 判断文件格式是否正确
   * 
   * @param contentType
   * @return
   */
  protected static boolean isImage(String contentType) {
    if (StringUtil.isEmpty(contentType))
      return false;
    for (String type : IMAGETYPE) {
      if (type.equals(contentType)) {
        return true;
      }
    }
    return false;
  }

  protected boolean isPost(HttpServletRequest request) {
	if (request.getMethod().indexOf("P") > -1) {
		return true;
	}
	return false;
  }
  
  /**
   * 获取文件后缀
   * 
   * @param contentType
   * @return
   */
  protected static String getFileExt(String contentType) {
    if (StringUtil.isEmpty(contentType))
      return "";
    for (String type : IMAGETYPE) {
      if (type.equals(contentType)) {
        if (contentType.equals("image/gif"))
          return "gif";
        else if (contentType.equals("image/png")
            || contentType.equals("image/x-png"))
          return "png";
        else
          return "jpg";
      }
    }
    return "";
  }

  /**
   * 图片上传，并同步到图片库，入库管理
   * 
   * @param row
   * @param request
   * @return
   */
  protected Row upload(Row row, HttpServletRequest request) {
  	// 楼盘库后台上传图片不分城市，统一放入一个地方
   	String rootdir = "/home/htmlfile";
    String[] files = (String[]) row.get("files");// 要上传的文件控件名列表
    String topicid = row.gets("topicid");// 品牌id,从配置文件拿
    String name = row.gets("name");// 图集名,对应产品名
    int setid = row.getInt("setid");// 图集ID
    int width_b = row.getInt("w_b");// 大图宽度
    int height_b = row.getInt("h_b");// 大图高度
    int width_t = row.getInt("w");// 缩略图宽度
    int height_t = row.getInt("h");// 缩略图高度
    int set_w = ParamProxy.geti("SET_SPHOTO_W");//图库缩略图宽度
    int set_h = ParamProxy.geti("SET_SPHOTO_H");//图库缩略图高度
    Row fileRow = new Row();

    if (files != null && files.length > 0) {// 存在
      try {
        Img2Service basic = new Img2Service();
        Imagick imagick = new Imagick();

        for (int i = 0; files != null && i < files.length; i++) {
          String file = request.getParameter(Constant.FILE_PRE + files[i]);
          if (StringUtil.isEmpty(file))
            continue;
          String contentType = request.getParameter(Constant.FILE_PRE + files[i]
              + ".content-type");
          logger.info(contentType);
          if (!isImage(contentType))
            continue;
          if (setid <= 0) {// 没有录入图集ID则创建
          	if(Constant.getCmsChannelId().equals("0011")){ //手机的图集ID特殊处理
          		setid = basic.createMobilephotosetNew(name, topicid);
          	}else{
	            setid = basic.createphotoset(name, topicid,
	                Constant.getCmsChannelId());
          	}
            fileRow.put("setid", setid);
          }
          String ext = getFileExt(contentType);// 后缀名
          String photoid = basic.getPhotoid(topicid);// 从图片库获取图片名
          String filename = photoid + "." + ext;// 原图名称
          String t_filename = "t_" + filename;// 缩略图名称
          String s_filename = "s_" + filename;//图库缩略图名称
          String dist = getFilePath() + "/" + filename;
          String t_dist = getFilePath() + "/" + t_filename;
          String s_dist = getFilePath() + "/" + s_filename;
          FileUtil.copy(file, rootdir + dist, true);
          // 压缩图片的处理,这里捕获异常是为了方便本地调试
          try {
            imagick.thumbnail(rootdir + dist, rootdir
                + dist, width_b, height_b);
            imagick.thumbnail(rootdir + dist, rootdir
                + t_dist, width_t, height_t);
            if(set_w > 0 && set_h > 0){
              imagick.cropRect(rootdir + dist,rootdir
                  + s_dist,set_w,set_h,0,0);
            }
            basic.addphoto(setid, name, ext, photoid, Constant.getCmsChannelId());// 保存到图片库的数据库
          } catch (Exception e) {
            logger.error("ERROR:对图片进行压缩时出现异常！");
          }
          fileRow.put(files[i], dist);
          // 默认设置大图的缩略图为小图路径,若小图也上传,则以小图的缩略图为准
          if (files[i].equals("bphoto") || files[i].equals("sphoto")) {
            File t_file = new File(rootdir + t_dist);
            if (t_file.exists()) {
              fileRow.put("sphoto", t_dist);
            }
          }
        }
      } catch (Exception e) {
        fileRow = null;
        logger.error("上传图片时出现异常！", e);
      }
    }
    logger.info(fileRow);
    return fileRow;
  }

  /**
   * 图片上传，同步到图片库服务器但不入数据库
   * 
   * @param row
   *          控件名列表(files[])、图片宽高(files[i]_w,files[i]_h)、缩略图宽高(files[i]_s_w,files[i]_s_h)
   * @param request
   * @return
   */
  protected Row uploadNotAdd(Row row, HttpServletRequest request) {
  	// 楼盘库后台上传图片不分城市，统一放入一个地方
  	String rootdir = "/home/htmlfile";
    String[] files = (String[]) row.get("files");// 要上传的文件控件名列表
    Row fileRow = new Row();
    String parent = row.gets("parent");
    if (files != null && files.length > 0) {// 存在
      try {
        Imagick imagick = new Imagick();
        for (int i = 0; files != null && i < files.length; i++) {
          String file = request.getParameter(Constant.FILE_PRE + files[i]);
          //logger.debug(file);
          if (StringUtil.isEmpty(file))
            continue;
          String contentType = request.getParameter(Constant.FILE_PRE + files[i]
              + ".content-type");
          if (!isImage(contentType))
            continue;
          String ext = getFileExt(contentType);// 后缀名
          String filename = System.currentTimeMillis() + "." + ext;// 原图名称
          int w = row.getInt(files[i] + "_w");// 大图宽度
          int h = row.getInt(files[i] + "_h");// 大图高度
          int s_w = row.getInt(files[i] + "_s_w");// 大图缩略图宽度
          int s_h = row.getInt(files[i] + "_s_h");// 大图缩略图高度
          String dist = getFilePath(parent) + "/" + filename;
          FileUtil.copy(file, rootdir + dist, true);
          try {
            if (w > 0 && h > 0) {
              imagick.thumbnail(rootdir + dist, rootdir + dist, w, h);
            }
            if (s_w > 0 && s_h > 0) {// 压缩缩略图
              String t_filename = "t_" + filename;// 缩略图名称
              String t_dist = getFilePath(parent) + "/" + t_filename;
              imagick.thumbnail(rootdir + dist, rootdir + t_dist, s_w, s_h);
            }
          } catch (Exception e) {
            logger.error("ERROR:对图片进行压缩时出现异常！");
          }
          fileRow.put(files[i], dist);
        }
      } catch (Exception e) {
        fileRow = null;
        logger.error("上传图片时出现异常！", e);
      }
    }
    logger.info(fileRow);
    return fileRow;
  }
}
