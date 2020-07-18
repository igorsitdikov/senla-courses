package com.senla.hotel.ui;

import com.senla.hotel.AppContext;
import com.senla.hotel.Application;
import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.controller.HotelController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.repository.ResidentRepository;
import com.senla.hotel.repository.RoomHistoryRepository;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.repository.interfaces.IResidentRepository;
import com.senla.hotel.repository.interfaces.IRoomHistoryRepository;
import com.senla.hotel.repository.interfaces.IRoomRepository;
import com.senla.hotel.service.AttendanceService;
import com.senla.hotel.service.HotelAdminService;
import com.senla.hotel.service.ResidentService;
import com.senla.hotel.service.RoomHistoryService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.interfaces.IAttendanceService;
import com.senla.hotel.service.interfaces.IHotelAdminService;
import com.senla.hotel.service.interfaces.IResidentService;
import com.senla.hotel.service.interfaces.IRoomHistoryService;
import com.senla.hotel.service.interfaces.IRoomService;
import com.senla.hotel.ui.action.admin.CalculateBillAction;
import com.senla.hotel.ui.action.admin.CheckInAction;
import com.senla.hotel.ui.action.admin.CheckOutAction;
import com.senla.hotel.ui.action.attendance.AddAttendanceAction;
import com.senla.hotel.ui.action.attendance.ChangeAttendancePriceAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesAction;
import com.senla.hotel.ui.action.attendance.ShowAttendancesSortedByNameAction;
import com.senla.hotel.ui.action.resident.AddAttendanceToResidentAction;
import com.senla.hotel.ui.action.resident.AddResidentAction;
import com.senla.hotel.ui.action.resident.CountResidentsAction;
import com.senla.hotel.ui.action.resident.ShowResidentsAction;
import com.senla.hotel.ui.action.resident.ShowResidentsSortedByCheckOutDateAction;
import com.senla.hotel.ui.action.resident.ShowResidentsSortedByNameAction;
import com.senla.hotel.ui.action.room.AddRoomAction;
import com.senla.hotel.ui.action.room.ChangePriceAction;
import com.senla.hotel.ui.action.room.CountVacantAction;
import com.senla.hotel.ui.action.room.ShowDetailsAction;
import com.senla.hotel.ui.action.room.ShowRoomsAction;
import com.senla.hotel.ui.action.room.ShowRoomsSortedByAccommodationAction;
import com.senla.hotel.ui.action.room.ShowRoomsSortedByPriceAction;
import com.senla.hotel.ui.action.room.ShowRoomsSortedByStarsAction;
import com.senla.hotel.ui.action.room.ShowVacantAction;
import com.senla.hotel.ui.action.room.ShowVacantOnDateAction;
import com.senla.hotel.ui.action.room.ShowVacantSortedByAccommodationAction;
import com.senla.hotel.ui.action.room.ShowVacantSortedByPriceAction;
import com.senla.hotel.ui.action.room.ShowVacantSortedByStarsAction;
import com.senla.hotel.ui.menu.Builder;
import com.senla.hotel.ui.menu.MenuController;
import com.senla.hotel.ui.menu.Navigator;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.SerializationUtils;
import com.senla.hotel.utils.csv.interfaces.ICsvReader;
import com.senla.hotel.utils.csv.interfaces.ICsvWriter;
import com.senla.hotel.utils.csv.reader.CsvReader;
import com.senla.hotel.utils.csv.writer.CsvWriter;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Map<Class<?>, Class<?>> CLASS_MAP = new HashMap<>();

    public static void main(String[] args) throws Exception {
        configure();
        AppContext context = Application.run("com.senla.hotel", CLASS_MAP);
        MenuController menuController = context.getObject(MenuController.class);
        menuController.setContext(context);
        menuController.run();
    }

    public static void configure() {
        CLASS_MAP.put(ICsvReader.class, CsvReader.class);
        CLASS_MAP.put(ICsvWriter.class, CsvWriter.class);
        CLASS_MAP.put(IResidentRepository.class, ResidentRepository.class);
        CLASS_MAP.put(IRoomHistoryRepository.class, RoomHistoryRepository.class);
        CLASS_MAP.put(IRoomRepository.class, RoomRepository.class);
        CLASS_MAP.put(ParseUtils.class, ParseUtils.class);
        CLASS_MAP.put(SerializationUtils.class, SerializationUtils.class);
        CLASS_MAP.put(IAttendanceService.class, AttendanceService.class);
        CLASS_MAP.put(IHotelAdminService.class, HotelAdminService.class);
        CLASS_MAP.put(IResidentService.class, ResidentService.class);
        CLASS_MAP.put(IRoomHistoryService.class, RoomHistoryService.class);
        CLASS_MAP.put(IRoomService.class, RoomService.class);
        CLASS_MAP.put(AttendanceController.class, AttendanceController.class);
        CLASS_MAP.put(HotelController.class, HotelController.class);
        CLASS_MAP.put(ResidentController.class, ResidentController.class);
        CLASS_MAP.put(RoomController.class, RoomController.class);
        CLASS_MAP.put(AddAttendanceAction.class, AddAttendanceAction.class);
        CLASS_MAP.put(ChangeAttendancePriceAction.class, ChangeAttendancePriceAction.class);
        CLASS_MAP.put(ShowAttendancesAction.class, ShowAttendancesAction.class);
        CLASS_MAP.put(ShowAttendancesSortedByNameAction.class, ShowAttendancesSortedByNameAction.class);
        CLASS_MAP.put(AddResidentAction.class, AddResidentAction.class);
        CLASS_MAP.put(CountResidentsAction.class, CountResidentsAction.class);
        CLASS_MAP.put(AddAttendanceToResidentAction.class, AddAttendanceToResidentAction.class);
        CLASS_MAP.put(ShowResidentsAction.class, ShowResidentsAction.class);
        CLASS_MAP.put(ShowResidentsSortedByNameAction.class, ShowResidentsSortedByNameAction.class);
        CLASS_MAP.put(ShowResidentsSortedByCheckOutDateAction.class, ShowResidentsSortedByCheckOutDateAction.class);
        CLASS_MAP.put(CheckInAction.class, CheckInAction.class);
        CLASS_MAP.put(CheckOutAction.class, CheckOutAction.class);
        CLASS_MAP.put(CalculateBillAction.class, CalculateBillAction.class);
        CLASS_MAP.put(AddRoomAction.class, AddRoomAction.class);
        CLASS_MAP.put(ChangePriceAction.class, ChangePriceAction.class);
        CLASS_MAP.put(CountVacantAction.class, CountVacantAction.class);
        CLASS_MAP.put(ShowDetailsAction.class, ShowDetailsAction.class);
        CLASS_MAP.put(ShowRoomsAction.class, ShowRoomsAction.class);
        CLASS_MAP.put(ShowRoomsSortedByAccommodationAction.class, ShowRoomsSortedByAccommodationAction.class);
        CLASS_MAP.put(ShowRoomsSortedByPriceAction.class, ShowRoomsSortedByPriceAction.class);
        CLASS_MAP.put(ShowRoomsSortedByStarsAction.class, ShowRoomsSortedByStarsAction.class);
        CLASS_MAP.put(ShowVacantAction.class, ShowVacantAction.class);
        CLASS_MAP.put(ShowVacantOnDateAction.class, ShowVacantOnDateAction.class);
        CLASS_MAP.put(ShowVacantSortedByAccommodationAction.class, ShowVacantSortedByAccommodationAction.class);
        CLASS_MAP.put(ShowVacantSortedByPriceAction.class, ShowVacantSortedByPriceAction.class);
        CLASS_MAP.put(ShowVacantSortedByStarsAction.class, ShowVacantSortedByStarsAction.class);
        CLASS_MAP.put(Navigator.class, Navigator.class);
        CLASS_MAP.put(Builder.class, Builder.class);
        CLASS_MAP.put(MenuController.class, MenuController.class);
    }
}
