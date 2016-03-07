#pragma once
#include "interfaces.h"
#include "iid.h"
#include <iostream>

class CoCar : public IEngine, public ICreateCar, public IStats {
private:
	ULONG m_refCount;
	BSTR m_petName; //инициализация через SysAllocString(), удаление через SysFreeString()
	int m_maxSpeed;
	int m_currSpeed;
	static const int MAX_SPEED = 300;
	static const int MAX_NAME_LENGTH = 100;

public:
	CoCar();
	virtual ~CoCar();

	//IUnknown
	STDMETHODIMP QueryInterface(REFIID riid, void **pIFace);
	STDMETHODIMP_(DWORD)AddRef();
	STDMETHODIMP_(DWORD)Release();

	//IEngine
	STDMETHODIMP SpeedUp();
	STDMETHODIMP GetMaxSpeed(int *maxSpeed);
	STDMETHODIMP GetCurSpeed(int *curSpeed);

	//IStats
	STDMETHODIMP DisplayStats();
	STDMETHODIMP GetPetName(BSTR *petName);

	//ICreateCar
	STDMETHODIMP SetPetName(BSTR petName);
	STDMETHODIMP SetMaxSpeed(int maxSpeed);
};