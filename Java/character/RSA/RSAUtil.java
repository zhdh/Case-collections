import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *	RSA 加密解密
 */
public class RSAUtil {

	/** 公钥存放位置 */
	private static String PUBLIC_KEY_FILE = "publicKey";
	/** 私钥存放位置 */
	private static String PRIVATE_KEY_FILE = "privateKey";

	/**
	 * 设置公私钥存放位置
	 * 
	 * @param path
	 *            路径
	 */
	public static void setKeyPath(String path) {
		if (PUBLIC_KEY_FILE.equals("publicKey")) {
			PUBLIC_KEY_FILE = path + (path.endsWith("//") ? "PublicKey" : "/PublicKey");
			PRIVATE_KEY_FILE = path + (path.endsWith("//") ? "PrivateKey" : "/PrivateKey");
		}
	}

	/**
	 * 创建公私秘钥对
	 */
	private static void createKeyPair() throws NoSuchAlgorithmException, FileNotFoundException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair generateKeyPair = keyPairGenerator.generateKeyPair();
		PublicKey publicKey = generateKeyPair.getPublic();
		PrivateKey privateKey = generateKeyPair.getPrivate();
		ObjectOutputStream oosByPublicKey = null;
		ObjectOutputStream oosByPrivateKey = null;
		try {
			oosByPublicKey = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
			oosByPrivateKey = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));
			oosByPublicKey.writeObject(publicKey);
			oosByPrivateKey.writeObject(privateKey);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				oosByPublicKey.close();
				oosByPrivateKey.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** RSA 加密 */
	public static String encryptWithRSA(String str) throws NoSuchAlgorithmException, ClassNotFoundException,
			IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		createKeyPair();
		Key publicKey = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
			publicKey = (Key) ois.readObject();
		} catch (IOException e) {
			throw new RuntimeException();
		} finally {
			ois.close();
		}
		// 获取为RSA加密算法的解密器对象
		Cipher cipher = Cipher.getInstance("RSA");
		// 设置加密模式,及公钥
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// 得到密文
		byte[] secret = cipher.doFinal(str.getBytes());
		// 进行Base64编码
		return new BASE64Encoder().encode(secret);
	}

	/** RSA 解密 */
	public static String decryptWithRSA(String secret)
			throws ClassNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Key privateKey = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
			privateKey = (Key) ois.readObject();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			ois.close();
		}
		Cipher cipher = Cipher.getInstance("RSA");
		// 传递私钥，设置为解密模式。
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		// 解密Base64解码后密文,获得明文字节数组
		byte[] b = cipher.doFinal(new BASE64Decoder().decodeBuffer(secret));
		// 转换成字符串
		return new String(b);
	}

	public static void main(String[] args) throws Exception {
		System.out.print("输入明文: ");
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		String str = in.nextLine();
		System.out.println("---------------------------");
		String secret = encryptWithRSA(str);
		System.out.println("RSA 加密后的密文: ");
		System.out.println(secret);
		System.out.println("---------------------------");
		String original = decryptWithRSA(secret);
		System.out.println("RSA 解密后的原文：");
		System.out.println(original);

	}

}
