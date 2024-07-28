package Helper;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Helper {

	public static void optionPaneChangeButtonText() {
		UIManager.put("OptionPane.cancelButtonText", "İptal");
		UIManager.put("OptionPane.noButtonText", "Hayır");
		UIManager.put("OptionPane.okButtonText", "Tamam");
		UIManager.put("OptionPane.yesButtonText", "Evet");
	}

	public static void showMsg(String str) {
		optionPaneChangeButtonText();
		String msg;

		switch (str) {
		case "fill":
			msg = "Lütfen tüm alanları doldurunuz.";
			break;
		case "invalid":
			msg = "Hatalı kullanıcı adı veya şifre !";
			break;
		case "invalid number":
			msg = "Lütfen Geçerli Sayı Giriniz !";
			break;
		case "success":
			msg = "İşlem başarılı !";
			break;
		case "no stock":
			msg = "Stokta yeterli ürün yok.";
			break;
		case "select employee":
			msg = "Lütfen bir çalışan seçin!";
			break;
		case "success sale product":
			msg = "Ürün satışı başarıyla gerçekleştirildi.";
			break;
		case "error sale product":
			msg = "Ürün satışı sırasında bir hata oluştu.";
			break;
		case "add product":
			msg = "Lütfen ürün ekleyin!";
			break;
		default:
			msg = str;
		}
		JOptionPane.showMessageDialog(null, msg, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
	}

	public static boolean confirm(String str) {
		optionPaneChangeButtonText();
		String msg = null;
		switch (str) {
		case "sure":
			msg = "Bu işlemi gerçekleştirmek istiyor musunuz ?";
			break;
		case "updateSure":
			msg = "Ürünü güncellemek istediğinize emin misiniz?";
			break;
		case "cancel":
			msg = "Yaptığınız işlemler iptal edilecektir. Emin misiniz?";
			break;
		}

		int res = JOptionPane.showConfirmDialog(null, msg, "Dikkat !", JOptionPane.YES_NO_OPTION);

		if (res == 0) {
			return true;
		} else {
			return false;
		}
	}
}