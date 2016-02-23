#include "interfaces.h"
#include "iid.h"

class CoCar : public IEngine, public ICreateCar, public IStats {
private:
	ULONG m_refCount;

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

CoCar::CoCar() {
	m_refCount = 0;
}

CoCar::~CoCar() {

}

STDMETHODIMP_(DWORD) CoCar::AddRef() {
	return ++m_refCount;
}

STDMETHODIMP_(DWORD) CoCar::Release() {
	return (--m_refCount == 0) ? (delete this, 0) : m_refCount;
}

STDMETHODIMP CoCar::QueryInterface(REFIID riid, void **pIFace) {
	if (riid == IID_IUnknown) {
		MessageBox(NULL, (LPCWSTR)"Handed out IUnknown", (LPCWSTR)"QI", MB_OK | MB_SETFOREGROUND);
		*pIFace = (IUnknown*)(IEngine*) this;
	} else if (riid == IID_IEngine) {
		MessageBox(NULL, (LPCWSTR)"Handed out IEngine", (LPCWSTR)"QI", MB_OK | MB_SETFOREGROUND);
		*pIFace = (IEngine*) this;
	} else if (riid == IID_IStats) {
		MessageBox(NULL, (LPCWSTR)"Handed out IStats", (LPCWSTR)"QI", MB_OK | MB_SETFOREGROUND);
		*pIFace = (IStats*) this;
	} else if (riid == IID_ICreateCar) {
		MessageBox(NULL, (LPCWSTR)"Handed out ICreateCar", (LPCWSTR)"QI", MB_OK | MB_SETFOREGROUND);
		*pIFace = (ICreateCar*) this;
	} else {
		*pIFace = NULL;
		return E_NOINTERFACE;
	}

	((IUnknown*)(*pIFace))->AddRef();
	return S_OK;
}

