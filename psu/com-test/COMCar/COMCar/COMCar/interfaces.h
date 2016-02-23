#ifndef _INTERFACES
#define _INTERFACES

#include <windows.h>

//IStats ������������ ��� ��������� ����� ����������, � �����
//��� ����������� ���������� ��������� ������������ �������
DECLARE_INTERFACE_(IStats, IUnknown) {
	STDMETHOD(DisplayStats)() PURE;
	STDMETHOD(GetPetName)(BSTR* petName) PURE;
};

//IEngine ���������� ��������� ���������. ����� �������� ���,
//�������� �������� ������������ � ������� ��������
DECLARE_INTERFACE_(IEngine, IUnknown) {
	STDMETHOD(SpeedUp) () PURE;
	STDMETHOD(GetMaxSpeed) (int *maxSpeed) PURE;
	STDMETHOD(GetCurrentSpeed) (int *curSpeed) PURE;
};

//ICreateCar ������������ ��� ���������� ����� ������������
//������� � ������� ������������ ��������
DECLARE_INTERFACE_(ICreateCar, IUnknown) {
	STDMETHOD(SetPetName) (BSTR petName) PURE;
	STDMETHOD(SetMaxSpeed) (int maxSpeed) PURE;
}

#endif // !_INTERFACES
