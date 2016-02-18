/**
 * Version    Date      By        Description
 * ======================================================================================
 *            20090608  hrchen    ��ӷ���getFilePath(String)��uploadNotAdd(Row, HttpServletRequest, String)
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
    // ���ﲻ�ӵĻ���AJAX�Ỻ��ÿ��ȡ�����ݣ��Ӷ�������ݲ��ܸ���
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
   * ���ǰһҳ��
   * 
   * @return
   */
  protected String getReferer(HttpServletRequest request) {
    return request.getHeader("REFERER");
  }

  /** ҳ���ض��� */
  protected String sendRedirect(String url, HttpServletResponse response) {
    try {
    	response.sendRedirect(url);
    } catch (Exception e) {
      logger.error(e);
    }
    return null;
  }
  
  /**
   * ��ת��404ҳ��
   * @param response
   * @return
   */
  protected String sendTo404(HttpServletResponse response){
    String url = "http://" + SpObserver.getCity() + ".house.163.com/special/0087jt/error_house.html";
    return sendRedirect(url,response);
  }

  /**
   * ����
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
   * ����
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
   * ���JS����
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
   * �Ӵ��ڷ������ݣ���ֵ���������ں󣬹ر��Ӵ���
   * 
   * @param response
   * @param content
   * @param url
   * @throws Exception
   */
  public void renderScriptText(String content, HttpServletResponse response) {
    renderText("<script>document.write('<div style=\"font-size:12px;text-align:center;color:red\">"
        + content
        + "<br><input type=\"button\" value=\"ȷ�����رմ���\" onclick=\"parent.location.href=parent.location.href;parent.Dialog.close();\"></div>');</script>", response);
  }

  /**
   * ������ʾ��󷵻���һҳ�沢ˢ�£���֤�˲������һ�ν�����һҳ����ͬ��
   * 
   * @param content
   */
  public void alert(String content, HttpServletResponse response) {
    String text = "<script>alert('" + content
        + "');window.history.back();location.reload();</script>";
    renderText(text, response);
  }

  /**
   * ��ȡ��¼���û���
   * 
   * @return
   */
  public String getLoginUserName(HttpServletRequest request) {
    Row adminRow = Constant.FACADE.chklogin(request);
    return adminRow.gets("username", "");
  }

  /**
   * ��ȡ��½�û���Ƶ��
   * 
   * @return
   */
  public String getUserChannel(HttpServletRequest request) {
    return Constant.FACADE.chklogin(request).gets("channel", "");
  }
  /**
   * ��ȡͼƬ�ϴ��������������·��
   * @param parent��Ŀ¼
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
   * �ж��ļ���ʽ�Ƿ���ȷ
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
   * ��ȡ�ļ���׺
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
   * ͼƬ�ϴ�����ͬ����ͼƬ�⣬������
   * 
   * @param row
   * @param request
   * @return
   */
  protected Row upload(Row row, HttpServletRequest request) {
  	// ¥�̿��̨�ϴ�ͼƬ���ֳ��У�ͳһ����һ���ط�
   	String rootdir = "/home/htmlfile";
    String[] files = (String[]) row.get("files");// Ҫ�ϴ����ļ��ؼ����б�
    String topicid = row.gets("topicid");// Ʒ��id,�������ļ���
    String name = row.gets("name");// ͼ����,��Ӧ��Ʒ��
    int setid = row.getInt("setid");// ͼ��ID
    int width_b = row.getInt("w_b");// ��ͼ���
    int height_b = row.getInt("h_b");// ��ͼ�߶�
    int width_t = row.getInt("w");// ����ͼ���
    int height_t = row.getInt("h");// ����ͼ�߶�
    int set_w = ParamProxy.geti("SET_SPHOTO_W");//ͼ������ͼ���
    int set_h = ParamProxy.geti("SET_SPHOTO_H");//ͼ������ͼ�߶�
    Row fileRow = new Row();

    if (files != null && files.length > 0) {// ����
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
          if (setid <= 0) {// û��¼��ͼ��ID�򴴽�
          	if(Constant.getCmsChannelId().equals("0011")){ //�ֻ���ͼ��ID���⴦��
          		setid = basic.createMobilephotosetNew(name, topicid);
          	}else{
	            setid = basic.createphotoset(name, topicid,
	                Constant.getCmsChannelId());
          	}
            fileRow.put("setid", setid);
          }
          String ext = getFileExt(contentType);// ��׺��
          String photoid = basic.getPhotoid(topicid);// ��ͼƬ���ȡͼƬ��
          String filename = photoid + "." + ext;// ԭͼ����
          String t_filename = "t_" + filename;// ����ͼ����
          String s_filename = "s_" + filename;//ͼ������ͼ����
          String dist = getFilePath() + "/" + filename;
          String t_dist = getFilePath() + "/" + t_filename;
          String s_dist = getFilePath() + "/" + s_filename;
          FileUtil.copy(file, rootdir + dist, true);
          // ѹ��ͼƬ�Ĵ���,���ﲶ���쳣��Ϊ�˷��㱾�ص���
          try {
            imagick.thumbnail(rootdir + dist, rootdir
                + dist, width_b, height_b);
            imagick.thumbnail(rootdir + dist, rootdir
                + t_dist, width_t, height_t);
            if(set_w > 0 && set_h > 0){
              imagick.cropRect(rootdir + dist,rootdir
                  + s_dist,set_w,set_h,0,0);
            }
            basic.addphoto(setid, name, ext, photoid, Constant.getCmsChannelId());// ���浽ͼƬ������ݿ�
          } catch (Exception e) {
            logger.error("ERROR:��ͼƬ����ѹ��ʱ�����쳣��");
          }
          fileRow.put(files[i], dist);
          // Ĭ�����ô�ͼ������ͼΪСͼ·��,��СͼҲ�ϴ�,����Сͼ������ͼΪ׼
          if (files[i].equals("bphoto") || files[i].equals("sphoto")) {
            File t_file = new File(rootdir + t_dist);
            if (t_file.exists()) {
              fileRow.put("sphoto", t_dist);
            }
          }
        }
      } catch (Exception e) {
        fileRow = null;
        logger.error("�ϴ�ͼƬʱ�����쳣��", e);
      }
    }
    logger.info(fileRow);
    return fileRow;
  }

  /**
   * ͼƬ�ϴ���ͬ����ͼƬ����������������ݿ�
   * 
   * @param row
   *          �ؼ����б�(files[])��ͼƬ���(files[i]_w,files[i]_h)������ͼ���(files[i]_s_w,files[i]_s_h)
   * @param request
   * @return
   */
  protected Row uploadNotAdd(Row row, HttpServletRequest request) {
  	// ¥�̿��̨�ϴ�ͼƬ���ֳ��У�ͳһ����һ���ط�
  	String rootdir = "/home/htmlfile";
    String[] files = (String[]) row.get("files");// Ҫ�ϴ����ļ��ؼ����б�
    Row fileRow = new Row();
    String parent = row.gets("parent");
    if (files != null && files.length > 0) {// ����
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
          String ext = getFileExt(contentType);// ��׺��
          String filename = System.currentTimeMillis() + "." + ext;// ԭͼ����
          int w = row.getInt(files[i] + "_w");// ��ͼ���
          int h = row.getInt(files[i] + "_h");// ��ͼ�߶�
          int s_w = row.getInt(files[i] + "_s_w");// ��ͼ����ͼ���
          int s_h = row.getInt(files[i] + "_s_h");// ��ͼ����ͼ�߶�
          String dist = getFilePath(parent) + "/" + filename;
          FileUtil.copy(file, rootdir + dist, true);
          try {
            if (w > 0 && h > 0) {
              imagick.thumbnail(rootdir + dist, rootdir + dist, w, h);
            }
            if (s_w > 0 && s_h > 0) {// ѹ������ͼ
              String t_filename = "t_" + filename;// ����ͼ����
              String t_dist = getFilePath(parent) + "/" + t_filename;
              imagick.thumbnail(rootdir + dist, rootdir + t_dist, s_w, s_h);
            }
          } catch (Exception e) {
            logger.error("ERROR:��ͼƬ����ѹ��ʱ�����쳣��");
          }
          fileRow.put(files[i], dist);
        }
      } catch (Exception e) {
        fileRow = null;
        logger.error("�ϴ�ͼƬʱ�����쳣��", e);
      }
    }
    logger.info(fileRow);
    return fileRow;
  }
}
