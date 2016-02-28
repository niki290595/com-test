#pragma once
#include <windows.h>
#include "iid.h"
#include "CoCar.h"

class CoCarClassFactory : public IClassFactory {
private:
	DWORD m_refCount;

public:
	CoCarClassFactory();
	virtual ~CoCarClassFactory();

	//IUnknown
	STDMETHODIMP QueryInterface(REFIID riid, void **pIFace);
	STDMETHODIMP_(DWORD)AddRef();
	STDMETHODIMP_(DWORD)Release();

	//ICF
	STDMETHODIMP LockServer(BOOL fLock);
	STDMETHODIMP CreateInstance(LPUNKNOWN pUnkOuter, REFIID riid, void **ppv);
};