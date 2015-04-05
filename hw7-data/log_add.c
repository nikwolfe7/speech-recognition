#include <math.h>

/* Evaluate log(exp(left) + exp(right)) more accurately.
 * log(exp(left) + exp(right))
 *    = log(exp(left)) + log(1 + exp(right) / exp(left))
 *    = left + log(1 + exp(right - left))
 * Note: log1p(x) accurately computes log(1+x) for small x.
 */
double LogAdd(double left, double right) {
  if (right < left) {
    return left + log1p(exp(right - left));
  } else if (right > left) {
    return right + log1p(exp(left - right));
  } else {
    return left + M_LN2;
  }
}
