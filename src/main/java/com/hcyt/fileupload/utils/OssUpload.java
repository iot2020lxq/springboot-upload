package com.hcyt.fileupload.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Component
public class OssUpload {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${endpiont}")
	protected String endpiont;

	@Value("${accessId}")
	protected String accessId;

	@Value("${accessKey}")
	protected String accessKey;

	@Value("${bucket}")
	protected String bucket;

	/*
	 * endpiont oss的service address http://oss-cn-shenzhen.aliyuncs.com
	 * accessId为申请oss时的id
	 * accessKey为oss 的key
	 * uploaddir为文件存放文件夹
	 * url为cdn服务器地址
	 * */
	public String upLoad(String endpiont,String accessId,String accessKey,String bucket,String uploaddir,String url, MultipartFile upfile){
		InputStream ios = null;
		/**按uuid 生成新的文件名**/
		String uuidName = UUID.randomUUID().toString().replace("-", "");
		String getInputFileName=upfile.getOriginalFilename();
		//扩展名
		String extension = upfile.getOriginalFilename().substring(upfile.getOriginalFilename().lastIndexOf("."));
		//上传的目录地址
		String objectKey = uploaddir + uuidName + extension;
		try{
			ios = upfile.getInputStream();
			OSSClient client = new OSSClient(endpiont,accessId,accessKey);
			//client.createBucket(bucket);

			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(this.getExtensionType(extension));
			meta.setContentLength(upfile.getSize());
		
			//开始上传
			PutObjectResult pors=	client.putObject(bucket, objectKey, ios, meta);
		}catch(Exception e){
			System.out.println("上传错误");
			logger.error("OSS错误：" + "\r\n" + e.getMessage());
		}
		return url+objectKey;
	}
	
	/*
	 * endpiont oss的service address http://oss-cn-shenzhen.aliyuncs.com
	 * accessId为申请oss时的id
	 * accessKey为oss 的key
	 * uploaddir为文件存放文件夹
	 * url为cdn服务器地址
	 * */
	public String upLoad2(String endpiont,String accessId,String accessKey,String bucket,String uploaddir,String url, File upfile) throws Exception {
		InputStream ios = null;
		BufferedInputStream bis = null;
		/**按uuid 生成新的文件名**/
		String uuidName = UUID.randomUUID().toString().replace("-", "");
		String getInputFileName=upfile.getName();
		//扩展名
		String extension = upfile.getName().substring(upfile.getName().lastIndexOf("."));
		//上传的目录地址
		String objectKey = uploaddir + uuidName + extension;
		try{
			 ios=new FileInputStream(upfile);
			 bis = new BufferedInputStream(ios);
			OSSClient client = new OSSClient(endpiont,accessId,accessKey);
			//client.createBucket(bucket);

			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(this.getExtensionType(extension));
			//meta.setContentLength(upfile.getSize());
			//开始上传
			
			PutObjectResult pors=	client.putObject(new PutObjectRequest(bucket, objectKey, bis));

		}catch(Exception e){
			System.out.println("上传错误");
			logger.error("OSS错误：" + "\r\n" + e.getMessage());
			throw new Exception();
		}finally {
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(ios != null){
				try {
					ios.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return url+objectKey;
	}

	/**
	 * 获取文件扩展名类型
	 * @param extension
	 * @return
	 */
	private String getExtensionType(String extension) {
		switch(extension.toLowerCase()){
			case ".bmp":return "image/bmp";
			case ".gif":return "image/gif";
			case ".jpeg":return "image/jpeg";
			case ".jpg":return "image/jpeg";
			case ".png":return "image/jpeg";
			case ".html":return "text/html";
			case ".txt":return "text/plain";
			case ".xml":return "text/xml";
			case ".vsd": return "application/vnd.visio";
			default : return ".txt";
		}
	}
}


