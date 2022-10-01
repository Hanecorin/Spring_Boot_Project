package probono.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import probono.model.ProbonoService;
import probono.model.dto.ActivistDTO;
import probono.model.dto.RecipientDTO;

@Controller
@RequestMapping("probono")
public class ProbonoFrontController {

	@Autowired // ProbonoService 반드시 스프링 빈이어야 함
	private ProbonoService probonoService;
	 

	// 모두 ProbonoProject 검색 메소드
//	@RequestMapping("/probonoProjectAll")
//	public ModelAndView probonoProjectAll() throws Exception {
//		System.out.println("--------------------");
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("probonoProjectAll", probonoService.getAllProbonoProjects());
//		mv.setViewName("probonoProjectList");
//		return mv;
//	}

	// 모든 재능 기부자 검색 - 검색된 데이터 출력 화면[activistList.jsp]
//	@RequestMapping("/activistAll")
//	public ModelAndView activistAll() throws Exception {
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("activistAll", probonoService.getAllActivists());
//		mv.setViewName("activist/activistList");
//		return mv;
//	}

	// probono/activist?activistId=giver1
	// 재능 기부자 검색
//	@RequestMapping("/activist")
//	public ModelAndView activist(@RequestParam String activistId) throws Exception {
//		ModelAndView mv = new ModelAndView();
//		ActivistDTO a = probonoService.getActivist(activistId);
//		if (a != null) {
//			mv.addObject("activist", a);
//		} else {
//			throw new Exception("존재하지 않는 기부자입니다.");
//		}
//		mv.setViewName("activist/activistDetail");
//		return mv;
//	}

	// @ExceptionHandler
	// 예외 처리 전담 메소드
	/* 기능 : 모든 예외를 처리하는 메소드
	 * 구조 : Exception parameter로 선언 및 획득
	 * view 지정 : WEB/INF/showError.jsp
	 * controller 클래스의 모든 메소드의 예외 처리 중복 코드 제거 
	 */
	@ExceptionHandler
	public ModelAndView handler(Exception e) {
		e.printStackTrace();
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", e.getMessage());  //요청 객체에 발생된 예외 메세지 저장
		mv.setViewName("showError");

		return mv;
	}

	/*
	 * 중복 코드 제거 권장 - 예외 처리를 더 간소화도 가능 방식 : 예외 처리 전담 메소드 구현 서비스 로직의 메소드는 예외를 던짐
	 */
	@RequestMapping("/activistInsert")
	protected ModelAndView activistInsert(@ModelAttribute("activist") ActivistDTO activist) throws Exception {
		ModelAndView mv = new ModelAndView();

		if (activist.getId() != null && activist.getId().length() != 0 && activist.getName() != null
				&& activist.getName().length() != 0) {
			boolean result = probonoService.addActivist(activist);
			if (result) {
				mv.addObject("successMsg", "가입 완료");
			}
		}
		mv.setViewName("activist/activistDetail");
		return mv;
	}

	// probono/activistUpdateReq?activistId=giver1
	// 재능 기부자 수정 요구
	@RequestMapping("/activistUpdateReq")
	public ModelAndView activistUpdateReq(@RequestParam String activistId) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("activist", probonoService.getActivist(activistId));
		mv.setViewName("activist/activistUpdate");
		return mv;
	}

	// probono/activistUpdate : form tag의 데이터값 activistId, major 제공
	// 재능 기부자 수정 - 상세정보 확인 jsp[activistDetail.jsp]
	@RequestMapping("/activistUpdate")
	public ModelAndView activistUpdate(@RequestParam String activistId, @RequestParam String major) throws Exception {
		ModelAndView mv = new ModelAndView();
		if (probonoService.updateActivist(activistId, major)) {
			mv.addObject("activist", probonoService.getActivist(activistId));
		} else {
			throw new Exception( "저장 실패");
		}
		mv.setViewName("activist/activistDetail");
		return mv;
	}

	// 재능 기부자 삭제
	@RequestMapping("/activistDelete")
	public ModelAndView activistDelete(@RequestParam String activistId) {
		ModelAndView mv = new ModelAndView();
		String url = "showError";

		try {
			if (probonoService.deleteActivist(activistId)) {
				mv.addObject("activistAll", probonoService.getAllActivists());
				url = "activist/activistList";
			} else {
				mv.addObject("errorMsg", "저장 실패");
			}
		} catch (Exception s) {
			mv.addObject("errorMsg", s.getMessage());
			s.printStackTrace();
		}

		mv.setViewName(url);
		return mv;
	}

	// 모든 재능 수혜자 검색 - 검색된 데이터 출력 화면[activistList.jsp]
	
	
	@RequestMapping("/recipientAll")
	public ModelAndView recipientAll() {

		ModelAndView mv = new ModelAndView();
		String url = "showError";

		try {
			mv.addObject("recipientAll", probonoService.getAllRecipients());
			url = "recipient/recipientList";
		} catch (Exception s) {
			mv.addObject("errorMsg", s.getMessage());
			s.printStackTrace();
		}

		mv.setViewName(url);
		return mv;
	}

	// 재능 수혜자 검색
	@RequestMapping("/recipient")
	public ModelAndView recipient(@RequestParam String recipientId) {
		ModelAndView mv = new ModelAndView();
		String url = "showError";

		try {
			RecipientDTO r = probonoService.getRecipient(recipientId);
			if (r != null) {
				mv.addObject("recipient", r);
				url = "recipient/recipientDetail";
			} else {
				mv.addObject("errorMsg", "존재하지 않는 수혜자입니다.");
			}
		} catch (Exception s) {
			mv.addObject("errorMsg", s.getMessage());
			s.printStackTrace();
		}

		mv.setViewName(url);
		return mv;
	}

	

	
	
	// 재능 수혜자 가입 메소드
	@RequestMapping("/recipientInsert")
	protected ModelAndView recipientInsert(@ModelAttribute("recipient") RecipientDTO recipient) {
		ModelAndView mv = new ModelAndView();
		String url = "showError";

		// 해킹등으로 불합리하게 요청도 될수 있다는 가정하에 모든 데이터가 제대로 전송이 되었는지를 검증하는 로직
		if (recipient.getId() != null && recipient.getId().length() != 0 && recipient.getName() != null) {

			try {
				boolean result = probonoService.addRecipient(recipient);

				if (result) {
					mv.addObject("recipient", recipient);
					mv.addObject("successMsg", "가입 완료");
					url = "recipient/recipientDetail";
				} else {
					mv.addObject("errorMsg", "다시 시도하세요");
				}
			} catch (Exception s) {
				mv.addObject("errorMsg", s.getMessage());
			}

		}
		mv.setViewName(url);
		return mv;
	}

	// 재능 수혜자 수정 요구
	@RequestMapping("/recipientUpdateReq")
	public ModelAndView recipientUpdateReq(@RequestParam String recipientId) {

		ModelAndView mv = new ModelAndView();
		String url = "showError";

		try {
			mv.addObject("recipient", probonoService.getRecipient(recipientId));
			url = "recipient/recipientUpdate";

		} catch (Exception s) {
			mv.addObject("errorMsg", s.getMessage());
			s.printStackTrace();
		}

		mv.setViewName(url);
		return mv;

	}

	// 재능 수혜자 수정 - 상세정보 확인 jsp[activistDetail.jsp]
	@RequestMapping("/recipientUpdate")
	public ModelAndView recipientUpdate(@RequestParam String recipientId, @RequestParam String receiveHopeContent) {

		ModelAndView mv = new ModelAndView();
		String url = "showError";

		try {
			boolean result = probonoService.updateRecipient(recipientId, receiveHopeContent);

			if (result) {
				mv.addObject("recipient", probonoService.getRecipient(recipientId));
				url = "recipient/recipientDetail";
			} else {
				mv.addObject("errorMsg", "수정 실패");
			}
		} catch (Exception s) {
			mv.addObject("errorMsg", s.getMessage());
			s.printStackTrace();
		}

		mv.setViewName(url);
		return mv;
	}

	
	
	// 재능 수혜자 삭제
	@RequestMapping("/recipientDelete")
	public ModelAndView recipientDelete(@RequestParam String recipientId) {
		ModelAndView mv = new ModelAndView();
		String url = "showError";

		try {
			boolean result = probonoService.deleteRecipient(recipientId);
			if (result) {
				mv.addObject("recipientAll", probonoService.getAllRecipients());
				url = "recipient/recipientList";
			} else {
				mv.addObject("errorMsg", "삭제 실패");
			}
		} catch (Exception s) {
			mv.addObject("errorMsg", s.getMessage());
			s.printStackTrace();
		}

		mv.setViewName(url);
		return mv;
	}
}