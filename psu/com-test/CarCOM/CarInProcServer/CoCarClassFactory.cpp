#include "CoCarClassFactory.h"
#include "CoCar.h"

extern DWORD g_lockCount;
extern DWORD g_objCount;

CoCarClassFactory::CoCarClassFactory() {
	m_refCount = 0;
	g_objCount++;
}

CoCarClassFactory::~CoCarClassFactory() {
	g_objCount--;
}

//IUnknown
STDMETHODIMP_(DWORD) CoCarClassFactory::AddRef() {
	return ++m_refCount;
}

STDMETHODIMP_(DWORD) CoCarClassFactory::Release() {
	return (--m_refCount == 0) ? (delete this, 0) : m_refCount;
}

STDMETHODIMP CoCarClassFactory::QueryInterface(REFIID riid, void **ppv) {
	if (riid == IID_IUnknown) {
		*ppv = (IUnknown*) this;
	} else if (riid == IID_IClassFactory) {
		*ppv = (IClassFactory*) this;
	} else {
		*ppv = NULL;
		return E_NOINTERFACE;
	}

	((IUnknown*)(*ppv))->AddRef();
	return S_OK;
}

//IClassFactory
STDMETHODIMP CoCarClassFactory::LockServer(BOOL fLock) {
	g_lockCount += fLock ? 1 : -1;
	return S_OK;
}

STDMETHODIMP CoCarClassFactory::CreateInstance(LPUNKNOWN pUnkOuter, REFIID riid, void **ppv) {
	if (pUnkOuter != NULL)
		return CLASS_E_NOAGGREGATION;

	CoCar *pCarObj = new CoCar();
	HRESULT hr = pCarObj->QueryInterface(riid, ppv);

	if (FAILED(hr))
		delete pCarObj;
	return hr;
}