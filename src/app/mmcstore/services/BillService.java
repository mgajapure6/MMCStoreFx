package app.mmcstore.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Persistence;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import app.mmcstore.dao.BillDao;
import app.mmcstore.dao.BillProviderProductDao;
import app.mmcstore.dto.CustomerDashboardDto;
import app.mmcstore.dto.ProductDto;
import app.mmcstore.entity.Bill;
import app.mmcstore.entity.BillProviderProduct;
import app.mmcstore.entity.Customer;

public class BillService {

	private BillDao billDao;
	private BillProviderProductDao billProviderProductDao;

	public BillService() {
		try {
			billDao = new BillDao(Persistence.createEntityManagerFactory("MMCStore"));
			billProviderProductDao = new BillProviderProductDao(Persistence.createEntityManagerFactory("MMCStore"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public Boolean saveBill(Customer customer, Set<BillProviderProduct> billProviderProductSet, boolean isPaid) {
		Bill bill = new Bill();
		bill.setCustomer(customer);
		bill.setBillDate(new Date());
		bill.setIsPaid(isPaid);
		try {
			billDao.create(bill);
			for (BillProviderProduct billProviderProduct : billProviderProductSet) {
				billProviderProduct.setBill(bill);
				billProviderProductDao.create(billProviderProduct);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Bill> getBillsByCustomerId(Integer customerId) {
		String query = "select billId, billDate,isPaid,sum(amount) as billAmount "
				+ "from(SELECT a.billId, a.billDate, a.isPaid, b.qtyRequested * e.price as amount "
				+ "from bill a join billproviderproduct b on (a.billId = b.billId) "
				+ "join providerproduct c on (b.providerProductId = c.providerProductId) "
				+ "join product e on (c.productId = e.productId) where a.customerId = " + customerId
				+ ")t group by billId";

		List bills = null;
		try {
			List bllll = billDao.executeQuery(query, "CustomerBillDtoMapping");
			bills = getBillTypedList(bllll);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bills;
	}

	@SuppressWarnings("rawtypes")
	public List<ProductDto> getBillDetailById(Integer billId) {
		String query = "SELECT a.billId, a.billDate, c.productId, e.productName, "
				+ "b.qtyRequested as qty, e.price as rate, e.description, "
				+ "b.qtyRequested * e.price as amount from bill a join billproviderproduct b "
				+ "on (a.billId = b.billId) join providerproduct c " + "on (b.providerProductId = c.providerProductId) "
				+ "join provider d on (c.providerId = d.providerId) "
				+ "join product e on (c.productId = e.productId) where a.billId =" + billId;

		List<ProductDto> pdt = null;
		try {
			List lll = billDao.executeQuery(query, "ProductDtoMapping");
			pdt = getBillProductsList(lll);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdt;
	}

	@SuppressWarnings("rawtypes")
	public CustomerDashboardDto getCustomerBillDashboardDetail(Integer customerId) {
		String query = "select sum(totalBills) as totalBill, sum(paidBills) as paidBill,"
				+ "sum(unpaidBills) as unpaidBill "
				+ "from( select count(*) as totalBills,0 as paidBills,0 as unpaidBills "
				+ "from bill a join billproviderproduct b on(a.BILLID=b.billId) "
				+ "where a.customerId = 1 group by a.BILLID "
				+ "union ALL select 0 as totalBills, count(*) as paidBills, 0 as unpaidBills "
				+ "from bill a where a.customerId = 1 and a.ISPAID > 0 group by a.BILLID UNION "
				+ "select 0 as totalBills, 0 as paidBills, count(*) as unpaidBills "
				+ "from bill a where a.customerId = " + customerId + " and a.ISPAID < 1 group by a.BILLID )t";

		CustomerDashboardDto cddto = null;
		try {
			List lll = billDao.executeQuery(query, "CustomerDashboardDtoMapping");
			cddto = getCustomerDashboardDto(lll);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cddto;
	}

	@SuppressWarnings("rawtypes")
	public Long getCustomerUnpaidBillCountByCustomerId(Integer cid) {
		Long count = 0l;
		String query = "select count(*) as unpaidBillCount from bill a where a.customerId = " + cid
				+ " and a.ISPAID !=1";
		try {
			List l = billDao.executeQuery(query);
			count = (Long) l.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("rawtypes")
	private List<Bill> getBillTypedList(List list) {
		List<Bill> bills = new ArrayList<>();
		Gson gson = new Gson();
		for (Object object : list) {
			JsonElement jsonElement = gson.toJsonTree(object);
			Bill b = gson.fromJson(jsonElement, Bill.class);
			bills.add(b);
		}
		return bills;
	}

	@SuppressWarnings("rawtypes")
	private List<ProductDto> getBillProductsList(List list) {
		List<ProductDto> pds = new ArrayList<>();
		Gson gson = new Gson();
		for (Object object : list) {
			JsonElement jsonElement = gson.toJsonTree(object);

			ProductDto b = gson.fromJson(jsonElement, ProductDto.class);
			pds.add(b);
		}
		return pds;
	}

	@SuppressWarnings("rawtypes")
	private CustomerDashboardDto getCustomerDashboardDto(List list) {
		CustomerDashboardDto cddto = new CustomerDashboardDto();
		Gson gson = new Gson();
		for (Object object : list) {
			JsonElement jsonElement = gson.toJsonTree(object);
			System.out.println("getCustomerDashboardDto jsonElement:"+jsonElement);
			cddto = gson.fromJson(jsonElement, CustomerDashboardDto.class);
		}
		return cddto;
	}

	public boolean updateOnlyBill(Bill selectedBill) {

		try {
			System.out.println("selectedBill::" + selectedBill);
			billDao.update(selectedBill);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
