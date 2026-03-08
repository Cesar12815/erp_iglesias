// feat: apply ISP to ApiService — split into domain-specific Angular services
// ADR-08: Interface Segregation Principle — modelos compartidos entre servicios

export interface LoginResponse { token: string; email: string; role: string; }
export interface Church { id: number; name: string; address: string; }
export interface User { id: number; email: string; role: string; }
export interface Person { id: number; firstName: string; lastName: string; document?: string; phone?: string; email?: string; churchId?: number; churchName?: string; }
export interface PersonPayload { firstName: string; lastName: string; document?: string; phone?: string; email?: string; }
export interface Course { id: number; name: string; description?: string; price: number; active: boolean; }
export interface CoursePayload { name: string; description?: string; price: number; }
export interface Enrollment { id: number; personId: number; personName: string; courseId: number; courseName: string; status: string; paymentId?: number; paymentStatus?: string; }
export interface EnrollmentPayload { personId: number; courseId: number; }
export interface Offering { id: number; personId: number; personName: string; concept: string; amount: string; status: string; paymentId?: number; paymentStatus?: string; }
export interface OfferingPayload { personId: number; amount: number; concept: string; }
export interface Payment { id: number; type: string; status: string; amount: string; attempts: number; referenceId: number; }
export interface Dashboard { totalPeople: number; activeCourses: number; offeringsMonth: number; pendingPayments: number; }
